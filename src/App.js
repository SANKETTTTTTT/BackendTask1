// src/components/App.js
import React, { useState } from 'react';
import TransactionsTable from './TransactionsTable';
import Statistics from './Statistics';
import BarChart from './BarChart';

const App = () => {
  const [month, setMonth] = useState('March');

  const handleMonthChange = (event) => {
    setMonth(event.target.value);
  };

  return (
    <div>
      <h1>Transactions Dashboard</h1>
      <label>
        Select Month:
        <select value={month} onChange={handleMonthChange}>
          <option value="January">January</option>
          <option value="February">February</option>
          <option value="March">March</option>
          <option value="April">April</option>
          <option value="May">May</option>
          <option value="June">June</option>
          <option value="July">July</option>
          <option value="August">August</option>
          <option value="September">September</option>
          <option value="October">October</option>
          <option value="November">November</option>
          <option value="December">December</option>
        </select>
      </label>
      <TransactionsTable month={month} />
      <Statistics month={month} />
      <BarChart month={month} />
    </div>
  );
};

export default App;
