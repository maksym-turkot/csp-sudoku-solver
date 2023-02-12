========================
Configuring the program
========================

Program automatically reads puzzle files and figures out puzzle type based on the filename. It takes no input from the terminal.

Puzzles are located in the data/puzzles folder. The filename of the puzzle should have the following form: 

<puzzleType>_<id>.txt
classic_3.txt

To have the program parse files correctly, puzzleType must spell “classic”, “triple”, or “killer”. Only classic puzzles can be parsed at the moment.

The classic puzzle file must contain cell numbers written in order, separated by a whitespace. Order of numbers must be as they appear in the puzzle: left to right, top to bottom. Empty cells should be represented by a “0”.  Additional whitespace can be added for readability.

========================
Running the program
========================

Program was developed using Java.

To run the program, please make sure you are located in the turkotm folder. The Makefile is provided, so you can run the program using the following commands:
make or make compile - compiles the program
make run - runs the program
make clean - removes compiled .class files.

When running, program will read puzzles from the folder, solve them, and write corresponding solutions to the “solutions” folder. Program execution data will be appended to the “log.txt” file.

Program Output
Solution file is very similar to the puzzle file, except that 0s are replaced with correct numbers, and filename includes a “-sol” suffix. Solution files are written to 
“data/solutions” folder.

Upon completion, program will record results in the “log.txt” file, located in the data folder. Each entry in the log file includes the aggregated runtime data for the solutions of one type of puzzles. 

For more info, please see the csp-sudocu-doc.pdf in the doc folder.