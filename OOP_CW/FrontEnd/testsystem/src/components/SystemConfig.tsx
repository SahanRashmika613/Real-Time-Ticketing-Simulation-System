import React from 'react';
import './SystemConfig.css';

export interface SystemConfigValues {
  totalTickets: string;
  releaseRate: string;
  retrievalRate: string;
  maxCapacity: string;
}

interface SystemConfigProps {
  values: SystemConfigValues;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
}

const SystemConfig: React.FC<SystemConfigProps> = ({ values, onChange, error }) => {
  const fields = [
    { label: 'Total Tickets', name: 'totalTickets' },
    { label: 'Release Rate (per second)', name: 'releaseRate' },
    { label: 'Retrieval Rate (per second)', name: 'retrievalRate' },
    { label: 'Max Capacity', name: 'maxCapacity' },
  ];

  return (
    <div className="system-config">
      <h2>System Configuration</h2>
      {fields.map((field) => (
        <div className="config-field" key={field.name}>
          <label>{field.label}</label>
          <input
            type="number"
            name={field.name}
            value={values[field.name as keyof SystemConfigValues] || ''}
            onChange={onChange}
          />
        </div>
      ))}

      {error && (
        <div className="error-message">
          <p>{error}</p>
        </div>
      )}
    </div>
  );
};

export default SystemConfig;