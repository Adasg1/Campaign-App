import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api';

export default function CampaignList({ fetchBalance }) {
    const [campaigns, setCampaigns] = useState([]);

    // Funkcja pobierająca listę kampanii
    const fetchCampaigns = () => {
        api.get('/campaigns')
            .then(response => setCampaigns(response.data))
            .catch(error => console.error("Błąd pobierania kampanii:", error));
    };

    useEffect(() => {
        fetchCampaigns();
    }, []);

    // Funkcja usuwająca kampanię
    const handleDelete = (id) => {
        if (window.confirm("Czy na pewno chcesz usunąć tę kampanię? Środki zostaną zwrócone na konto.")) {
            api.delete(`/campaigns/${id}`)
                .then(() => {
                    fetchCampaigns(); // Odśwież listę
                    fetchBalance();   // Odśwież stan konta na górze ekranu
                })
                .catch(error => console.error("Błąd usuwania:", error));
        }
    };

    return (
        <div>
            <h2>Lista Twoich Kampanii</h2>
            {campaigns.length === 0 ? (
                <p>Brak kampanii. Dodaj pierwszą!</p>
            ) : (
                <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                    <thead>
                    <tr style={{ backgroundColor: '#f8f9fa', textAlign: 'left' }}>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Nazwa</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Słowa kluczowe</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Bid (PLN)</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Budżet</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Status</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Miasto (Promień)</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Akcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    {campaigns.map(camp => (
                        <tr key={camp.id}>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>{camp.name}</td>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>{camp.keywords.join(', ')}</td>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>{camp.bidAmount}</td>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>{camp.campaignFund}</td>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>
                  <span style={{ color: camp.status ? 'green' : 'red', fontWeight: 'bold' }}>
                    {camp.status ? 'ON' : 'OFF'}
                  </span>
                            </td>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>{camp.town} ({camp.radius} km)</td>
                            <td style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>
                                <Link
                                    to={`/edit/${camp.id}`}
                                    style={{ marginRight: '10px', textDecoration: 'none', color: '#ffc107' }}>
                                    Edytuj
                                </Link>
                                <button
                                    onClick={() => handleDelete(camp.id)}
                                    style={{ background: 'none', border: 'none', color: '#dc3545', cursor: 'pointer', textDecoration: 'underline' }}>
                                    Usuń
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}