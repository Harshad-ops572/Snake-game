# 🐍 2D Classic Snake Game

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing%20%2F%20AWT-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey?style=for-the-badge)

A sleek, responsive **2D Classic Snake Game** built entirely in **pure Java** using standard **Swing** (`javax.swing`) and **AWT** (`java.awt`). 

Zero external frameworks, game engines, or third-party libraries required. Designed with clean object-oriented architecture, modular design, and built-in unit tests.

---

## ✨ Features

- **Smooth 2D Graphics**: Rendered with anti-aliasing via `Graphics2D`, featuring custom color gradients, rounded segment corners, shiny fruit with leaf reflections, and directional animated eyes.
- **Dynamic Difficulty**: Automatically accelerates game speed as the snake eats more apples and grows longer.
- **Suicide Guard**: Prevents instant 180° turns into the snake's own neck when pressing opposing keys rapidly.
- **Smart Food Spawner**: Guarantees apples never spawn inside the snake's body.
- **Score & High Score Tracking**: Keeps live score and personal best for the current session.
- **Pause & Resume**: Press `P` anytime to pause the action.
- **Game Over & Instant Restart**: Hit `SPACE` or `R` to jump right back into the game without restarting the app.
- **Automated Test Suite**: Comes with standalone unit tests to verify movement, collision math, and food spawning.

---

## 🎮 Controls

| Key | Action |
| :--- | :--- |
| **Arrow Keys** or **W / A / S / D** | Steer Snake (Up, Down, Left, Right) |
| **P** | Pause / Resume |
| **SPACE** or **R** | Restart after Game Over |

---

## 🏗️ Project Architecture

```
snake-game/
├── .gitignore          # Ignores compiled binaries and IDE configs
├── LICENSE             # MIT License
├── README.md           # Documentation
├── run.bat             # 1-Click launcher for Windows
├── run.sh              # 1-Click launcher for Linux / macOS
└── src/
    ├── Direction.java  # Cardinal movement enum & 180° reversal prevention
    ├── Food.java       # Apple coordinates & collision-free random spawning
    ├── GameFrame.java  # Desktop window setup (JFrame)
    ├── GamePanel.java  # Canvas rendering (Graphics2D) & Game Loop (Timer)
    ├── Main.java       # Application entry point (Event Dispatch Thread)
    ├── Snake.java      # Segment list, movement physics & self-collision detection
    └── TestSnake.java  # Headless unit verification tests
```

### Module Breakdown

| Class | Role | Key Functionality |
| :--- | :--- | :--- |
| [`Direction.java`](src/Direction.java) | Math & Vectors | Enum for `UP, DOWN, LEFT, RIGHT` + `isOpposite()` prevention logic. |
| [`Food.java`](src/Food.java) | Spawning | Places apple at random tile; checks `!snake.occupies()` so it never overlaps. |
| [`Snake.java`](src/Snake.java) | Physics Model | Manages `List<Point>`; adds new head at index 0 and trims tail; detects self-bite. |
| [`GamePanel.java`](src/GamePanel.java) | Loop & Canvas | `javax.swing.Timer` heartbeat; keyboard inputs; anti-aliased drawing. |
| [`GameFrame.java`](src/GameFrame.java) | Window Container | Sets up `JFrame` (title, dimensions, centering on screen). |
| [`Main.java`](src/Main.java) | Entry Point | Launches GUI safely on Swing's event dispatch thread. |
| [`TestSnake.java`](src/TestSnake.java) | Test Suite | Headless test runner to test movement, growth, and collision rules without GUI. |

---

## 🚀 Getting Started

### Prerequisites

Ensure you have a **Java Development Kit (JDK 8 or newer)** installed:

```bash
java -version
javac -version
```

### Option 1: 1-Click Launchers

- **Windows**: Double-click `run.bat`
- **Linux / macOS**:
  ```bash
  chmod +x run.sh
  ./run.sh
  ```

### Option 2: Manual Terminal Commands

#### 1. Compile
```bash
javac -d bin src/*.java
```

#### 2. Run the Game
```bash
java -cp bin Main
```

#### 3. Run Automated Tests
```bash
java -ea -cp bin TestSnake
```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
