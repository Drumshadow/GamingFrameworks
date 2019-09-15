# Game Engine
This is the game engine repository for Dr. Fendt's Fall 2019 Gaming Frameworks class.

**Engine Skillfully Built By:** Cody Dowell, Stevie Damrel, Weston Straw, and Ashley Roesler

# Current Features
- Drag-and-Drop Audio
- Partial Collision Detection and Gravity
- Basic Input Handling
- Functional Game Loop
- Sprite Drawing

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
    
    
# User's Guide
Here are the steps to play!
1) If you are compiling the source code on a Mac you will need to supply a 
JVM argument, -XstartOnFirstThread. This is because of how Mac and Windows
both handle threading in different ways.
2) Once the code is compiled and loaded feel free to move Mario around
to your favorite vapor wave music all while enjoying some timeless
meme sound effect classics.