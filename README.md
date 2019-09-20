# Game Engine
This is the game engine repository for Dr. Fendt's Fall 2019 Gaming Frameworks class.

**Engine Skillfully Built By:** Cody Dowell, Stevie Damrel, Weston Straw, and Ashley Roesler

# Current Features
- Drag-and-Drop Audio
- Accurate Collision Detection and Gravity
- Input Handling for Keyboard and Controllers 
- Functional Game Loop
- Sprite Drawing
- Basic HUD Drawing

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
    
# User's Guide
Here are the steps to play!
1) If you are compiling the source code on a Mac you will need to supply a 
JVM argument, -XstartOnFirstThread. This is because of how Mac and Windows
both handle threading in different ways.
2) Once the code is compiled and loaded feel free to move Mario around
to your favorite music all while enjoying some timeless
meme sound effect classics. Move with WASD.
3) Run into the right-most Mario and watch your HP drain from the bar in the top left of your screen.
