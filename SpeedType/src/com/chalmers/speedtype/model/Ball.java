package com.chalmers.speedtype.model;

public class Ball {

	private float xPos;
	private float yPos;
	private float xAcceleration;
	private float yAcceleration;
	private float lastPosX;
	private float lastPosY;
	private float oneMinusFriction;
	private long lastTime;
	private float lastDeltaTime;
	private float horizontalBound;
	private float verticalBound;

	Ball(float ballFriction, float horizontalBound, float verticalBound) {
		final float r = 0.1f;
		oneMinusFriction = 0.5f - ballFriction + r;
		this.horizontalBound = horizontalBound;
		this.verticalBound = verticalBound;
	}

	public float getPosX() {
		return xPos;
	}

	public float getPosY() {
		return yPos;
	}

	public void update(float sx, long now) {
		updatePositions(sx, now);
		resolveCollisionWithBounds();
	}

	public void setVerticalBound(float verticalBound) {
		this.verticalBound = verticalBound;
	}

	public void setHorizontalBound(float horizontalBound) {
		this.horizontalBound = horizontalBound;
	}

	private void updatePositions(float sx, long timestamp) {
		final long time = timestamp;
		if (lastTime != 0) {
			final float dT = (float) (time - lastTime) * (1.0f / 1000000000.0f);
			if (lastDeltaTime != 0) {
				computePhysics(sx, dT, dT / lastDeltaTime);
			}
			lastDeltaTime = dT;
		}
		lastTime = time;
	}

	public void computePhysics(float sx, float dT, float dTC) {
		final float m = 20.0f; // Gravitation/mass
		final float gx = -sx * m;
		final float gy = -2f * m;

		final float invm = 1.0f / m;
		final float ax = gx * invm;
		final float ay = gy * invm;

		final float dTdT = dT * dT;
		final float x = xPos + oneMinusFriction * dTC * (xPos - lastPosX)
				+ xAcceleration * dTdT;
		final float y = yPos + oneMinusFriction * dTC * (yPos - lastPosY)
				+ yAcceleration * dTdT;
		lastPosX = xPos;
		lastPosY = yPos;
		System.out.println(xPos + " " + yPos);
		xPos = x;
		yPos = y;
		System.out.println("xPos= " + xPos + "   " + "yPos= " + yPos);
		xAcceleration = ax;
		yAcceleration = ay;
	}

	public void resolveCollisionWithBounds() {

		final float x = xPos;
		final float y = yPos;
		final float xmax = horizontalBound;
		final float ymax = verticalBound;
		final float boardLevel = verticalBound * 6 / 8;
		if (x > xmax) { // Left of screen
			xPos = xmax;
			// endGame();
		} else if (x < -xmax) { // Right of screen
			xPos = -xmax;
			// endGame();
		}
		if (y > ymax) { // Top of screen
			yPos = ymax;
		} else if (y < boardLevel) { // Bottom of screen
			yPos = boardLevel;
		}
	}
}
