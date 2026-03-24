import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { api } from './api';

import CampaignList from './components/CampaignList';
import CampaignForm from './components/CampaignForm';

function App() {
  const [balance, setBalance] = useState(0);

  const fetchBalance = () => {
    api.get('/account/balance')
        .then(response => {
          setBalance(response.data.balance);
        })
        .catch(error => console.error("Błąd pobierania salda:", error));
  };

  useEffect(() => {
    fetchBalance();
  }, []);

  return (
      <BrowserRouter>
        <div style={{ maxWidth: '1000px', margin: '0 auto', padding: '20px', fontFamily: 'sans-serif' }}>

          <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '2px solid #eee', paddingBottom: '10px', marginBottom: '20px' }}>
            <div>
              <h1 style={{ margin: 0 }}>Emerald Campaigns</h1>
              <nav style={{ marginTop: '10px' }}>
                <Link to="/" style={{ marginRight: '15px', textDecoration: 'none', color: '#007bff' }}>Wszystkie kampanie</Link>
                <Link to="/add" style={{ textDecoration: 'none', color: '#007bff' }}>Dodaj nową</Link>
              </nav>
            </div>
            <div style={{ backgroundColor: '#e6f4ea', padding: '15px', borderRadius: '8px', border: '1px solid #ceead6' }}>
              <span style={{ display: 'block', fontSize: '12px', color: '#5f6368', textTransform: 'uppercase' }}>Emerald Account Funds</span>
              <strong style={{ fontSize: '24px', color: '#137333' }}>{balance.toFixed(2)} PLN</strong>
            </div>
          </header>

          <main>
            <Routes>
              <Route path="/" element={<CampaignList fetchBalance={fetchBalance} />} />
              <Route path="/add" element={<CampaignForm fetchBalance={fetchBalance} />} />
              <Route path="/edit/:id" element={<CampaignForm fetchBalance={fetchBalance} />} />
            </Routes>
          </main>

        </div>
      </BrowserRouter>
  );
}

export default App;
