import React from 'react';


interface HeaderProps {
  onStart: () => void;
  onStop: () => void;
}

const Header: React.FC<HeaderProps> = ({ onStart, onStop }) => {
  return (
    <header className="bg-gray-900 text-white p-6 rounded-lg min-h-screen flex flex-col items-center">
      <h1 className="text-3xl font-semibold mb-6">Real-Time Ticket System</h1>
      <div className="flex space-x-4">
        <button
          className="px-6 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition duration-300"
          onClick={onStart}
        >
          Start
        </button>
        <button
          className="px-6 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition duration-300"
          onClick={onStop}
        >
          Stop
        </button>
      </div>
    </header>
  );
};


export default Header;
