const express = require('express');
const cors = require('cors');
const app = express();
const db = require('./db.json');

const PORT = 2024;

app.use(express.json());

app.use(cors());

app.get('/responsables', (req, res) => {
  const { cedulaIdentidad } = req.query;
  
  if (cedulaIdentidad) {
    const responsable = db.responsables.find((r) => r.cedulaIdentidad === cedulaIdentidad);

    if (responsable) {
      //res.json({ responsables: [responsable] });
	  res.json(responsable);
    } else {
      res.json({ responsables: [] });
    }
  } else {
    res.json({ responsables: db.responsables });
  }
});

app.listen(PORT, () => {
  console.log(`Servidor iniciado en http://localhost:${PORT}`);
});