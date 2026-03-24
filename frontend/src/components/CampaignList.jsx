import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api';

export default function CampaignList({ fetchBalance }) {
    const [campaigns, setCampaigns] = useState([]);

    const fetchCampaigns = () => {
        api.get('/campaigns')
            .then(response => setCampaigns(response.data))
            .catch(error => console.error("Error loading campaign:", error));
    };

    useEffect(() => {
        fetchCampaigns();
    }, []);

    const handleDelete = (id) => {
        if (window.confirm("Are you sure you want to delete this campaign?. Funds will be returned to your account")) {
            api.delete(`/campaigns/${id}`)
                .then(() => {
                    fetchCampaigns();
                    fetchBalance();
                })
                .catch(error => console.error("Błąd usuwania:", error));
        }
    };

    return (
        <div>
            <h2>List of your Campaigns</h2>
            {campaigns.length === 0 ? (
                <p>No campaigns. Add your first</p>
            ) : (
                <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                    <thead>
                    <tr style={{ backgroundColor: '#f8f9fa', textAlign: 'left' }}>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Campaign name</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Keywords</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Bid amount ($)</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Campaign Fund</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Status</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>City (Radius)</th>
                        <th style={{ padding: '10px', borderBottom: '1px solid #ddd' }}>Actions</th>
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
                                    Edit
                                </Link>
                                <button
                                    onClick={() => handleDelete(camp.id)}
                                    style={{ background: 'none', border: 'none', color: '#dc3545', cursor: 'pointer', textDecoration: 'underline' }}>
                                    Delete
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