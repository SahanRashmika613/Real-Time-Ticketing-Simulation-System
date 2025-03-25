import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';
import CurrentStatus from './components/CurrentStatus';
import ActivityLog from './components/ActivityLog';
import SystemConfig from './components/SystemConfig';

const App: React.FC = () => {
  const [status, setStatus] = useState({
    running: false,
    tickets: 0,
  });

  const [config, setConfig] = useState({
    totalTickets: '',
    releaseRate: '',
    retrievalRate: '',
    maxCapacity: '',
  });

  const [logs, setLogs] = useState<string[]>([]);
  const [enabled, setEnabled] = useState(false); // Polling state (initially false)
  const [error, setError] = useState<string>(''); // To hold error message

  const handleStart = async () => {
    // Validate that all required fields are filled
    if (
      !config.totalTickets ||
      !config.releaseRate ||
      !config.retrievalRate ||
      !config.maxCapacity
    ) {
      // If any field is empty, show alert and prevent starting
      alert('Please fill out all fields before starting the system.');
      setError('Please fill out all fields before starting the system.'); 
      return;
    }

    // Clear any previous errors
    setError('');

    // Start the system
    setStatus({
      running: true,
      tickets: 0,
    });

    setLogs((prevLogs) => [...prevLogs, 'System started']);
    setEnabled(true); // Enable polling once the system starts

    try {
      const response = await axios.post('http://localhost:8081/api/v1/simulating/start', config);
      setLogs((prevLogs) => [...prevLogs, `Simulation started successfully: ${response.data}`]);
    } catch (error: any) {
      setLogs((prevLogs) => [
        ...prevLogs,
        `Error starting simulation: ${error.response ? error.response.data : error.message}`,
      ]);
    }
  };

  const handleStop = () => {
    setStatus({
      running: false,
      tickets: 0,
    });
    setLogs([...logs, 'System stopped']);
    setEnabled(false); // Disable polling when the system stops
  };

  const handleConfigChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setConfig({ ...config, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    // Polling logic, only runs if `enabled` is true (when system is started)
    if (enabled) {
      const interval = setInterval(() => {
        setStatus((prevStatus) => ({
          ...prevStatus,
          tickets: prevStatus.tickets + 1, // Increment tickets as a mock behavior
        }));
      }, 1000);

      // Clean up interval when polling is disabled or component unmounts
      return () => clearInterval(interval);
    }
  }, [enabled]);

  return (
    <div className="app">
      <header className="header">
        <h1 className="title">Real-Time Ticket System</h1>
        <div className="buttons">
          <button
            className="start-button"
            onClick={handleStart}
            disabled={
              enabled || // Disable if already enabled (system running)
              !config.totalTickets || // Disable if totalTickets is empty
              !config.releaseRate || // Disable if releaseRate is empty
              !config.retrievalRate || // Disable if retrievalRate is empty
              !config.maxCapacity // Disable if maxCapacity is empty
            }
          >
            Start System
          </button>
          <button className="stop-button" onClick={handleStop} disabled={!status.running}>
            Stop System
          </button>
        </div>

        {/* Error message - should be visible when there's an error */}
        {error && (
          <div className="error-message">
            <p>{error}</p>
          </div>
        )}
      </header>

      <div className="main-content">
        <SystemConfig values={config} onChange={handleConfigChange} error={error} />
        <CurrentStatus status={status} />
        <ActivityLog logs={logs} />
      </div>
    </div>
  );
};

export default App;
