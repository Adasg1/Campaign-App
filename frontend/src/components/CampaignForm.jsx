import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Select from 'react-select';
import { api } from '../api';

export default function CampaignForm({ fetchBalance }) {
    const navigate = useNavigate();
    const { id } = useParams();
    const isEditMode = Boolean(id);

    const [formData, setFormData] = useState({
        name: '',
        keywords: [],
        bidAmount: '',
        campaignFund: '',
        status: true,
        town: '',
        radius: ''
    });

    const [townOptions, setTownOptions] = useState([]);
    const [keywordOptions, setKeywordOptions] = useState([]);

    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        api.get('/dictionaries/towns').then(res => setTownOptions(res.data));

        api.get('/dictionaries/keywords').then(res => {
            const formattedKeywords = res.data.map(kw => ({ value: kw, label: kw }));
            setKeywordOptions(formattedKeywords);
        });

        if (isEditMode) {
            api.get(`/campaigns/${id}`)
                .then(res => {
                    const camp = res.data;
                    setFormData({
                        name: camp.name,
                        keywords: camp.keywords.map(kw => ({ value: kw, label: kw })),
                        bidAmount: camp.bidAmount,
                        campaignFund: camp.campaignFund,
                        status: camp.status,
                        town: camp.town,
                        radius: camp.radius
                    });
                })
                .catch(err => console.error("Błąd pobierania kampanii:", err));
        }
    }, [id, isEditMode]);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData({
            ...formData,
            [name]: type === 'checkbox' ? checked : value
        });
    };

    const handleKeywordsChange = (selectedOptions) => {
        setFormData({ ...formData, keywords: selectedOptions || [] });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setErrorMessage('');

        const payload = {
            ...formData,
            keywords: formData.keywords.map(k => k.value),
            bidAmount: parseFloat(formData.bidAmount),
            campaignFund: parseFloat(formData.campaignFund),
            radius: parseInt(formData.radius, 10)
        };

        const request = isEditMode
            ? api.put(`/campaigns/${id}`, payload)
            : api.post('/campaigns', payload);

        request
            .then(() => {
                fetchBalance();
                navigate('/');
            })
            .catch(error => {
                console.error("Błąd zapisu:", error);
                if (error.response && error.response.data && error.response.data.message) {
                    setErrorMessage(error.response.data.message);
                } else {
                    setErrorMessage("Wystąpił błąd podczas zapisywania kampanii. Sprawdź konsole.");
                }
            });
    };

    const formGroupStyle = { marginBottom: '15px' };
    const labelStyle = { display: 'block', marginBottom: '5px', fontWeight: 'bold' };
    const inputStyle = { width: '100%', padding: '8px', boxSizing: 'border-box' };

    return (
        <div style={{ maxWidth: '600px', margin: '0 auto', padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <h2>{isEditMode ? 'Edytuj Kampanię' : 'Dodaj Nową Kampanię'}</h2>

            {errorMessage && (
                <div style={{ backgroundColor: '#f8d7da', color: '#721c24', padding: '10px', marginBottom: '15px', borderRadius: '5px' }}>
                    {errorMessage}
                </div>
            )}

            <form onSubmit={handleSubmit}>
                <div style={formGroupStyle}>
                    <label style={labelStyle}>Nazwa kampanii *</label>
                    <input type="text" name="name" value={formData.name} onChange={handleChange} style={inputStyle} required />
                </div>

                <div style={formGroupStyle}>
                    <label style={labelStyle}>Słowa kluczowe (Typeahead) *</label>
                    <Select
                        isMulti
                        name="keywords"
                        options={keywordOptions}
                        value={formData.keywords}
                        onChange={handleKeywordsChange}
                        placeholder="Zacznij wpisywać..."
                        required={formData.keywords.length === 0}
                    />
                </div>

                <div style={formGroupStyle}>
                    <label style={labelStyle}>Bid amount (min. 10 PLN) *</label>
                    <input type="number" step="0.01" min="10" name="bidAmount" value={formData.bidAmount} onChange={handleChange} style={inputStyle} required />
                </div>

                <div style={formGroupStyle}>
                    <label style={labelStyle}>Budżet kampanii (Fund) *</label>
                    <input
                        type="number"
                        step="0.01"
                        min="0.1"
                        name="campaignFund"
                        value={formData.campaignFund}
                        onChange={handleChange}
                        style={inputStyle}
                        required
                        disabled={isEditMode}
                        title={isEditMode ? "Nie można zmieniać budżetu istniejącej kampanii" : ""}
                    />
                </div>

                <div style={formGroupStyle}>
                    <label style={{...labelStyle, display: 'inline-block', marginRight: '10px'}}>Status:</label>
                    <input type="checkbox" name="status" checked={formData.status} onChange={handleChange} />
                    <span style={{ marginLeft: '5px' }}>{formData.status ? 'ON' : 'OFF'}</span>
                </div>

                <div style={formGroupStyle}>
                    <label style={labelStyle}>Miasto *</label>
                    <select name="town" value={formData.town} onChange={handleChange} style={inputStyle} required>
                        <option value="">-- Wybierz miasto --</option>
                        {townOptions.map(t => <option key={t} value={t}>{t}</option>)}
                    </select>
                </div>

                <div style={formGroupStyle}>
                    <label style={labelStyle}>Promień (km) *</label>
                    <input type="number" min="1" name="radius" value={formData.radius} onChange={handleChange} style={inputStyle} required />
                </div>

                <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
                    <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                        Zapisz kampanię
                    </button>
                    <button type="button" onClick={() => navigate('/')} style={{ padding: '10px 20px', backgroundColor: '#6c757d', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                        Anuluj
                    </button>
                </div>
            </form>
        </div>
    );
}