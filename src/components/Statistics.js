// src/components/Statistics.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Statistics = ({ month }) => {
  const [stats, setStats] = useState({ totalSale: 0, soldItems: 0, notSoldItems: 0 });

  useEffect(() => {
    fetchStatistics();
  }, [month]);

  const fetchStatistics = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/statistics`, {
        params: { month }
      });
      setStats(response.data);
    } catch (error) {
      console.error('Error fetching statistics:', error);
    }
  };

  return (
    <div>
      <h3>Statistics for {month}</h3>
      <p>Total Sale Amount: ${stats.totalSale}</p>
      <p>Total Sold Items: {stats.soldItems}</p>
      <p>Total Not Sold Items: {stats.notSoldItems}</p>
    </div>
  );
};

export default Statistics;
