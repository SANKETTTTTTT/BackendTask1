// src/components/BarChart.js
import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import axios from 'axios';

const BarChart = ({ month }) => {
  const [chartData, setChartData] = useState({});

  useEffect(() => {
    fetchBarChartData();
  }, [month]);

  const fetchBarChartData = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/barchart`, {
        params: { month }
      });
      const data = response.data;
      setChartData({
        labels: Object.keys(data),
        datasets: [
          {
            label: 'Number of Items',
            data: Object.values(data),
            backgroundColor: 'rgba(75,192,192,0.6)'
          }
        ]
      });
    } catch (error) {
      console.error('Error fetching bar chart data:', error);
    }
  };

  return (
    <div>
      <h3>Bar Chart for {month}</h3>
      <Bar data={chartData} />
    </div>
  );
};

export default BarChart;
