import React from 'react';
import './CurrentStatus.css';

interface StatusProps {
  running: boolean;
  tickets: number;
}

const CurrentStatus: React.FC<{ status: StatusProps }> = ({ status }) => {
  return (
    <div className="current-status">
      <h2>Current Status</h2>
      <p className="status-info">
        System Status:{' '}
        <span className={status.running ? '' : 'stopped'}>
          {status.running ? 'RUNNING' : 'STOPPED'}
        </span>
      </p>
      <p className="status-info">Tickets Available: {status.tickets}</p>
    </div>
  );
};

export default CurrentStatus;
