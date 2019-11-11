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
    Adding/Changing Audio
    - If you're going to use OpenAL the file must be in a .ogg form
    - In the Gameloop class you can see a good example on how to add input functionality 
    along with how to connect them to a given sound
    - At the top of the Gameloop class you can see where the background music is created and started
    and if you want to change the background music it must be in the form of a
    .wav file

    Adding Sprites
    - Sprites can be uploaded in any form but must be associated with a game object
    - They should be added to the sprites folder
    
    Adding Inputs
    - Input handling is supported for the keyboard and the controller
    - Inputs can be added, edited or removed by using controller.ini or keyboard.ini
    - As of right now, 5 input actions are supported: Creating, Destroying, Move along the X axis,
    Move along the Y axis, and Play a Sound
    - For both files, you must declare the correct amount of inputs in the 'input' field of the 
    'control' section
    - All keyboard inputs must have:
        - A key to be pressed
        - An action for the key (pressing, releasing or holding down)
        - An object the input is bound to
        - The purpose of the input
        - If the purpose is to move, the speed you want the object to move at
        - If the purpose is to play sound, the file path of the sound file you wish to play
    - All controller inputs must have:
        - A bool declaring whether input is for button or not (1 for button, 0 for axes)
        - The index for the button or axes to be added
        - The range that the axes will activate in (if button, value does not matter)
        - An object the input is bound to
        - The purpose of the input
        - If the purpose is to move, the speed you want the object to move at
        - If the purpose is to play sound, the file path of the sound file you wish to play
    Edit/Add/Remove Objects
        - The game will look in a .ini file called objects.ini, this is where it is looking for everything from how 
        inputs are done to what objects, wven where they are.
        - To add an object simply type in everything need for the object, here is an example below 
        [object0]
        name=player
        sprPath=./sprites/friend.png
        collide=true
        fear=false
        weight=1.0
        tv=10
        jump=0.0
        boxType=0
        x=200.0
        y=1000.0
        - To edit an object you just edit the information important to it within the .ini file
        - To remove an object just delete the information for it from the .ini
    Exmaple on how to edit the player object
    1) Step one is to run the game.
    2) Step two is to then press "P"
        - Once "P" is pressed the geame will be ready to update upon pressing "O"
    3) Go into the .ini file and find the line that says "sprPath=./sprites/friend.png" and change it to the following
    "sprPath=./sprites/foe.png". Now your sprite will be changed!
    
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