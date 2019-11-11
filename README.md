# Game Engine
This is the game engine repository for Dr. Fendt's Fall 2019 Gaming Frameworks class.

**Engine Skillfully Built By:** Cody Dowell, Stevie Damrel, Weston Straw, and Ashley Roesler

# Current Features
- **GUI:** allows the user to edit and add game objects, events, inputs, HUD elements, AI, sounds, sprites, fonts, and backgrounds
- Functional pause button
- Animations
- **AI options for all game objects:**
  - COPY - an object copies the movements of another
  - LEDGES - an object turns around when it detects a ledge
  - WALLS - an object turns around when it collides with something horizontally
  - BOUNCE - an object turns around when it collides with something vertically
  - AUTO - an object moves automatically without user input
  - EMIT - an object emits another object
  - DESTRUCT - an object is destroyed by another
- Drawing text to screen
- End game conditions
- Concurrent audio
- Proper collision detection

# Developer's Guide
Here is the link to the tutorial for how to use our game engine!
https://docs.google.com/document/d/12KBGy3d9sBkPRVUFHalbUuELw0DZ6vi1oxGVSpgAZ6o/edit?usp=sharing

Here is the link to our dropbox!
https://baylor.box.com/s/0v7nzy9ekbv443373czsu63zb6oqne5a
    
# User's Guide (How to Play the Demo Game)
**Here are the steps to download!**
1) If you are compiling the source code on a Mac you will need to supply a 
JVM argument, `-XstartOnFirstThread`. This is because of how Mac and Windows
both handle threading in different ways.
2) Once the code is compiled and loaded, you are ready to play!

**Here are the steps to play!**
1) Move your player around with **WASD**.
2) Shoot hearts by pressing **Enter**.
    1) The hearts will disappear when you run into them.
    2) The blue enemy will be destroyed if it gets hit by a heart.
3) If you run into the blue enemy, your health will decrease! **NOTE: THE GAME WILL END IF YOU RUN OUT OF HEALTH!**
4) To replenish your health, collect the flower at the right side of the level.
5) When you touch the fire balls, they are destroyed.
6) If you touch the red enemy, your score goes up!
7) Pause the game by pressing **P** and unpause by pressing **O**.