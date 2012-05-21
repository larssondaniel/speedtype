package com.chalmers.speedtype.model;

public class Ball {

	private static final float GRAVITY = 20.0f;
	private static final float INVERTED_GRAVITY = 1.0f / GRAVITY;
	private static final float BALL_FRICTION = 0.1f;
	private static final float ONE_MINUS_FRICTION = 0.6f - BALL_FRICTION;

	private float xPos;
	private float yPos;
	private float xAcceleration;
	private float yAcceleration;
	private float lastPosX;
	private float lastPosY;

	private long lastTime;
	private float lastDeltaTime;

	protected float horizontalBound;
	protected float verticalBound;

	protected void updatePositions(float sx, long timestamp) {
		if (lastTime != 0) {
			float dT = (float) (timestamp - lastTime) * (1.0f / 1000000000.0f);
			if (lastDeltaTime != 0)
				computePhysics(sx, dT, dT / lastDeltaTime);
			lastDeltaTime = dT;
		}
		lastTime = timestamp;
	}

	protected void computePhysics(float sensorX, float dT, float dTC) {
		lastPosX = xPos;
		lastPosY = yPos;

		xPos = xPos + ONE_MINUS_FRICTION * dTC * (xPos - lastPosX)
				+ xAcceleration * dT * dT;
		yPos = yPos + ONE_MINUS_FRICTION * dTC * (yPos - lastPosY)
				+ yAcceleration * dT * dT;

		xAcceleration = -sensorX * GRAVITY * INVERTED_GRAVITY;
		yAcceleration = -2f * GRAVITY * INVERTED_GRAVITY;
	}

	protected void resolveCollisionWithBounds() {
		final float boardLevel = verticalBound * 6 / 8;
		if (xPos > horizontalBound) {
			xPos = horizontalBound;
		} else if (xPos < -horizontalBound) {
			xPos = -horizontalBound;
		}
		if (yPos > verticalBound) {
			yPos = verticalBound;
		} else if (yPos < boardLevel) {
			yPos = boardLevel;
		}
	}

	public float getPosX() {
		return xPos;
	}

	public float getPosY() {
		return yPos;
	}

	public void setBounds(float horizontalBound, float verticalBound) {
		this.horizontalBound = horizontalBound;
		this.verticalBound = verticalBound;
	}
}
