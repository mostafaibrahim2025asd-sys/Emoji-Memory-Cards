# Emoji Memory Cards - Android Game

A clean, responsive, and native Android memory matching game built using Kotlin and Android Studio. Designed with custom game logic, timer controls, and state management.

---

## Game Overview & Rules
- Grid Layout: 3x4 grid featuring 12 interactive cards (6 matching pairs).
- Core Mechanics: 
  - Match two identical emoji cards to gain a point.
  - Incorrect matches deduct a point.
  - Shuffle mechanism dynamically resets the board position upon starting/restarting.
- Countdown Challenge: Race against a 90-second countdown timer.

---

## Tech Stack & Concepts Applied
- Language: Kotlin
- IDE: Android Studio
- UI Components: GridLayout, TextView, Material Cards
- Concurrency & Timing: CountDownTimer, Handler (Delayed UI resets)
- Lifecycle Management: Handled background execution safely inside onPause & onDestroy to prevent memory leaks and background timer drain.

---

## App Architecture & UX Flow
1. Home Screen: Welcomes the user with quick navigation to start the match.
2. Gameplay Screen: Real-time updates for score, moves, and remaining time.
3. Game Over / Result Screen: Displays final outcome based on score and time completion.

---

## How to Run Locally
1. Clone the repository:
   git clone https://github.com/mostafaibrahim2025asd-sys/Emoji-Memory-Cards.git
2. Open the project in Android Studio.
3. Build and run on an Android Emulator or connected physical device (API Level 21+).

---

## Author
Developed by Mostafa Ibrahim
Computer Science Student | Mobile App Development Enthusiast
