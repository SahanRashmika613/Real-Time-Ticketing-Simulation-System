import { useState, useEffect } from 'react';
import SystemStatusService from '../services/SystemStatusService'; // Import service

// Define a type for system status
type SystemStatus = {
  systemStatus: string;
  ticketsAvailable: number;
};

type PollingResult = {
  status: SystemStatus | null;
  error: string | null;
};

const useSystemStatusPoller = (enabled: boolean, pollingInterval: number): PollingResult => {
  const [status, setStatus] = useState<SystemStatus | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!enabled) {
      setStatus(null);
      return;
    }

    const fetchSystemStatus = async () => {
      try {
        const data = await SystemStatusService.getSystemStatus();
        setStatus({
          systemStatus: data.systemStatus,
          ticketsAvailable: data.ticketsAvailable,
        });
        setError(null); // Reset error when fetch is successful
      } catch (error) {
        console.error('Error fetching system status:', error);
        setStatus(null);
        setError('Failed to fetch system status');
      }
    };

    const intervalId = setInterval(fetchSystemStatus, pollingInterval);

    // Cleanup function
    return () => clearInterval(intervalId);
  }, [enabled, pollingInterval]);

  return { status, error };
};

export default useSystemStatusPoller;
