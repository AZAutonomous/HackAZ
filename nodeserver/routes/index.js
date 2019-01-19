const express = require('express');
const path = require('path');
const fs = require('fs');

const router = express.Router();

let waypointFile = fs.readFileSync(path.resolve(__dirname, '../../HackAZ/sampleWaypoints.json'), 'utf8').toString();
let planeFile = fs.readFileSync(path.resolve(__dirname, '../../HackAZ/samplePlane.json'), 'utf8').toString();
let obstacleFile = fs.readFileSync(path.resolve(__dirname, '../../HackAZ/sampleObstacleStart.json'), 'utf8').toString();

const refreshRate = 250;

initObstacles();
initPlane();

const timerID = setInterval(() => {
  waypointFile = fs.readFileSync(path.resolve(__dirname, '../../HackAZ/sampleWaypoints.json'), 'utf8').toString();
  obstacleFile = fs.readFileSync(path.resolve(__dirname, '../../HackAZ/sampleObstacle.json'), 'utf8').toString();
  planeFile = fs.readFileSync(path.resolve(__dirname, '../../HackAZ/samplePlane.json'), 'utf8').toString();
}, refreshRate);

function initObstacles() {
  const obstacleJSON = JSON.parse(obstacleFile);

  const timerID = setInterval(() => {
    for (let i = 0; i < obstacleJSON.obstacles.length; i++) {
      obstacleJSON.obstacles[i].lat += obstacleJSON.obstacles[i].dlat;
      obstacleJSON.obstacles[i].long += obstacleJSON.obstacles[i].dlong;
      obstacleJSON.obstacles[i].currCycle++;

      if (obstacleJSON.obstacles[i].currCycle === obstacleJSON.obstacles[i].cycles) {
        obstacleJSON.obstacles[i].dlat *= -1;
        obstacleJSON.obstacles[i].dlong *= -1;
        obstacleJSON.obstacles[i].currCycle = 0;
      }
    }

    fs.writeFileSync(path.resolve(__dirname, '../../HackAZ/sampleObstacle.json'), JSON.stringify(obstacleJSON), 'utf8');
  }, refreshRate);
}

function initPlane() {
  const planeJSON = JSON.parse(planeFile);
  let waypointJSON = JSON.parse(waypointFile);
  let index = 0;

  planeJSON.plane.targetlat = waypointJSON.waypoints[index].lat;
  planeJSON.plane.targetlong = waypointJSON.waypoints[index].long;
  planeJSON.plane.lat = 20;
  planeJSON.plane.long = 20;

  const timerID = setInterval(() => {
    waypointJSON = JSON.parse(waypointFile);
    const ratio = planeJSON.plane.speed / Math.sqrt(((planeJSON.plane.targetlong - planeJSON.plane.long) ** 2) + ((planeJSON.plane.targetlat - planeJSON.plane.lat) ** 2));
    if (distance(planeJSON.plane.lat, planeJSON.plane.long, planeJSON.plane.targetlat, planeJSON.plane.targetlong) <= planeJSON.plane.speed) {
      planeJSON.plane.lat = planeJSON.plane.targetlat;
      planeJSON.plane.long = planeJSON.plane.targetlong;

      if (index < waypointJSON.waypoints.length - 1) index++;

      planeJSON.plane.targetlat = waypointJSON.waypoints[index].lat;
      planeJSON.plane.targetlong = waypointJSON.waypoints[index].long;
    } else if (ratio !== Infinity) {
      planeJSON.plane.lat += Math.floor((planeJSON.plane.targetlat - planeJSON.plane.lat) * ratio);
      planeJSON.plane.long += Math.floor((planeJSON.plane.targetlong - planeJSON.plane.long) * ratio);
    }

    fs.writeFileSync(path.resolve(__dirname, '../../HackAZ/samplePlane.json'), JSON.stringify(planeJSON), 'utf8');
  }, refreshRate);
}

function distance(lat1, long1, lat2, long2) {
  return Math.sqrt(((lat1 - lat2) ** 2) + ((long1 - long2) ** 2));
}

/* GET home page. */
router.get('/', (req, res, next) => {
  res.render(index);
});

router.get('/waypoints', (req, res, next) => {
  res.json(JSON.parse(waypointFile));
});

router.get('/plane', (req, res, next) => {
  res.json(JSON.parse(planeFile));
});

router.get('/obstacles', (req, res, next) => {
  res.json(JSON.parse(obstacleFile));
});

// router.post('/reset', (req, res, next) => {
//   console.log('yeet');
//   const planeJSON = JSON.parse(planeFile);
//   planeJSON.plane.lat = req.body.lat;
//   planeJSON.plane.long = req.body.long;

//   console.log(req.body.lat);
//   console.log(req.body.long);

//   fs.writeFileSync(path.resolve(__dirname, '../../HackAZ/samplePlane.json'), JSON.stringify(planeJSON), 'utf8');

//   res.json({ success: true });
// });

module.exports = router;
