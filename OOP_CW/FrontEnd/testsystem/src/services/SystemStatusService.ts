import axios from 'axios';

// Define the structure of the system status response
interface SystemStatus {
  systemStatus: string;
  ticketsAvailable: number;
}

const BASE_URL = 'http://localhost:8081/api/v1/simulating';

// Function to fetch the system status
const getSystemStatus = async (): Promise<SystemStatus> => {
  try {
    const response = await axios.get(`${BASE_URL}/status`);
    return response.data;  // Return the system status data from the response
  } catch (error) {
    console.error('Error fetching system status:', error);
    throw new Error('Failed to fetch system status');
  }
};

// Function to start the simulation with the provided configuration
const startSimulation = async (config: Record<string, string>) => {
  try {
    const response = await axios.post(`${BASE_URL}/start`, config);
    return response.data;  // Return the response data from the start simulation request
  } catch (error) {
    console.error('Error starting simulation:', error);
    throw new Error('Failed to start simulation');
  }
};

// Function to stop the simulation
const stopSimulation = async () => {
  try {
    const response = await axios.post(`${BASE_URL}/stop`);
    return response.data;  // Return the response data from the stop simulation request
  } catch (error) {
    console.error('Error stopping simulation:', error);
    throw new Error('Failed to stop simulation');
  }
};

export default {
  getSystemStatus,
  startSimulation,
  stopSimulation,
};
