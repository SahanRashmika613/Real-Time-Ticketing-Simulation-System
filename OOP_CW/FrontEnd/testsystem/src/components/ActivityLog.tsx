import React from 'react';
import './ActivityLog.css';

const ActivityLog: React.FC<{ logs: string[] }> = ({ logs }) => {
  return (
    <div className="activity-log">
      <h2>Activity Log</h2>
      {logs.length === 0 ? (
        <p>No activity to display</p>
      ) : (
        <ul>
          {logs.map((log, index) => (
            <li key={index}>{log}</li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ActivityLog;