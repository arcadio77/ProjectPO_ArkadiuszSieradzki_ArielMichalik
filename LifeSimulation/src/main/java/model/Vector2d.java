package model;


public record Vector2d(int x, int y) {

    public String toString() {
        return "(%s,%s)".formatted(String.valueOf(this.x), String.valueOf(this.y));
    }


    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean isBiggerOrEqual(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }
    public boolean isBigger(Vector2d other) {
        return this.x > other.x && this.y > other.y;
    }

    public boolean isSmallerOrEqual(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean isSmaller(Vector2d other) {
        return this.x < other.x && this.y < other.y;
    }


    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2d vector2d)) return false;
        return x == vector2d.x && y == vector2d.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

