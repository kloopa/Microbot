# Lumbridge → Al-Kharid Gate Walk Script

A minimal [Microbot](https://github.com/chsami/Microbot) automation script that walks from Lumbridge to the Al-Kharid toll gate.

## Important warnings

- **Botting violates Jagex's Terms of Service.** Accounts can be permanently banned.
- This script is for **learning** how Microbot plugins work, not for unattended farming.
- Standard **RuneLite Plugin Hub** does not allow automation. You need the **Microbot** client fork.

## Prerequisites

| Tool | Version |
|------|---------|
| JDK | 17+ (development), targets Java 11 bytecode |
| IntelliJ IDEA | Community Edition (recommended) |
| Git | For cloning Microbot |
| OSRS account | Logged in via Jagex Launcher at least once |

## Setup (step by step)

### 1. Clone Microbot

```powershell
cd C:\Users\lloyd\Projects
git clone https://github.com/chsami/Microbot.git
cd Microbot
```

### 2. Copy this script into Microbot

Copy the three `.java` files from:

```
osrs-lumbridge-walker\src\net\runelite\client\plugins\microbot\lumbridgewalk\
```

Into:

```
Microbot\runelite-client\src\main\java\net\runelite\client\plugins\microbot\lumbridgewalk\
```

### 3. Open in IntelliJ

1. Open IntelliJ IDEA → **Open** → select `Microbot\build.gradle.kts`
2. Set **Project SDK** and **Gradle JVM** to JDK 17+
3. Wait for Gradle sync to finish (first sync can take several minutes)

### 4. Build and run the client

From a terminal in the Microbot folder:

```powershell
.\gradlew.bat :client:run
```

Or in IntelliJ: create a Gradle run configuration for task `:client:run`.

### 5. Log in and enable the plugin

1. Log in with your Jagex account (see [Microbot installation docs](https://github.com/chsami/Microbot/blob/main/docs/installation.md))
2. Stand in Lumbridge (e.g. near the castle or general store)
3. Open the plugin panel and search for **"Lumbridge Walk"**
4. Enable the plugin — the character should start walking east toward the gate

### 6. Gate requirements

- If you have **not** completed Prince Ali Rescue, carry **10 coins** for the toll
- The web walker should open the gate and handle the border guard dialogue automatically

## How it works

| Component | Role |
|-----------|------|
| `LumbridgeWalkPlugin` | RuneLite plugin lifecycle — starts/stops the script |
| `LumbridgeWalkScript` | Background thread that calls `Rs2Walker.walkTo()` |
| `LumbridgeWalkConfig` | Plugin settings panel |
| `Rs2Walker` | Microbot's pathfinder — collision maps, doors, gates, transports |
| `ShortestPathPlugin` | Underlying A* pathfinding with `transports.tsv` data |

The script calls:

```java
Rs2Walker.walkTo(new WorldPoint(3267, 3227, 0), 3);
```

`Rs2Walker` calculates a path from your current tile, clicks minimap waypoints, and handles obstacles (including the Al-Kharid gate) via the transport system.

## Libraries (already included in Microbot)

You do **not** add these yourself — they ship with Microbot:

- `net.runelite:client` — RuneLite client API
- `Rs2Walker`, `Rs2Player`, `Rs2Dialogue`, etc. — Microbot util layer
- Lombok — boilerplate reduction (`@Slf4j`, `@Inject`)
- Guice — dependency injection for plugins

## Next steps

- Add an overlay showing destination and status
- Use `Rs2Walker.walkWithState()` for finer control (pause, cancel, retry)
- Explore [Microbot examples](https://github.com/chsami/Microbot/tree/main/runelite-client/src/main/java/net/runelite/client/plugins/microbot/example)
- Read `runelite-client/.../microbot/AGENTS.md` for threading and API rules

## Troubleshooting

| Problem | Fix |
|---------|-----|
| Plugin not visible | Rebuild with `.\gradlew.bat :client:compileJava` and restart client |
| Walker stalls at gate | Ensure 10 coins; complete Prince Ali Rescue for free passage |
| Gradle sync fails | JDK 17+, stable internet, retry **Refresh Gradle Dependencies** |
| Client won't start | Run `.\gradlew.bat cleanAll` then `:client:run` |
