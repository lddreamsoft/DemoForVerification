<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>



<!DOCTYPE html>
<html>
<head>

<title></title>

</head>

<body style="width:500px;height:500px;">



	<script src="/hlhh/js/jquery-3.1.1.min.js"></script>
	<script src="/hlhh/js/threeCanvas.js"></script>
	<script src="/hlhh/js/snow.js"></script>

</body>






<script>

var SCREEN_WIDTH = window.innerWidth;
var SCREEN_HEIGHT = window.innerHeight;
var container;
var particle;//粒子
var camera;
var scene;
var renderer;
var starSnow = 1;
var particles = [];

var particleImage = new Image();
particleImage.src = '/hlhh/images/luckyShake/winning/redPackets.png';

	function loop() {
		for (var i = 0; i < particles.length; i++) {
			var particle = particles[i];
			particle.updatePhysics();

			with (particle.position) {
				if ((y < -1000) && starSnow) {
					y += 2000;
				}

				if (x > 1000)
					x -= 2000;
				else if (x < -1000)
					x += 2000;
				if (z > 1000)
					z -= 2000;
				else if (z < -1000)
					z += 2000;
			}
		}

		camera.lookAt(scene.position);

		renderer.render(scene, camera);
	}

	$(function() {

		

		//$.ajaxSetup({cache:false});

		container = document.createElement('div');//container：画布实例;
		document.body.appendChild(container);

		camera = new THREE.PerspectiveCamera(50, SCREEN_WIDTH / SCREEN_HEIGHT,
				1, 10000);
		camera.position.z = 1000;
		//camera.position.y = 50;

		scene = new THREE.Scene();
		scene.add(camera);

		renderer = new THREE.CanvasRenderer();
		renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		var material = new THREE.ParticleBasicMaterial({
			map : new THREE.Texture(particleImage)
		});

		for (var i = 0; i < 260; i++) {
			particle = new Particle3D(material);
			particle.position.x = Math.random() * 2000 - 1000;
			particle.position.z = Math.random() * 2000 - 1000;
			particle.position.y = Math.random() * 2000 - 1000;
			particle.scale.x = particle.scale.y = 1.0;
			scene.add(particle);
			particles.push(particle);
		}

		container.appendChild(renderer.domElement);
		setInterval(loop, 1000 / 40);
	});
</script>

</html>







