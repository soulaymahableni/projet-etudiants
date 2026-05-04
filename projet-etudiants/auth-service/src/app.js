require('dotenv').config();
const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');
const authRoutes = require('./routes/auth');

const app = express();
const PORT = process.env.PORT || 3001;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/authdb';

app.use(cors());
app.use(express.json());

app.get('/health', (req, res) => res.json({ status: 'UP' }));
app.use('/auth', authRoutes);

// Global error handler
app.use((err, req, res, next) => {
  console.error(err);
  res.status(err.status || 500).json({
    error: err.name || 'Error',
    message: err.message || 'Erreur interne du serveur'
  });
});

mongoose.connect(MONGO_URI)
  .then(() => {
    console.log('Connecte a MongoDB');
    app.listen(PORT, () => console.log(`auth-service ecoute sur le port ${PORT}`));
  })
  .catch(err => {
    console.error('Echec de connexion MongoDB :', err.message);
    process.exit(1);
  });

module.exports = app;
