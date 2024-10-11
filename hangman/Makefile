# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++17 -Wall -O2 -I./src

# Source files
SRC = src/main.cpp src/game.cpp

# Object files in the build/ directory
OBJ = $(SRC:src/%.cpp=build/%.o)

# Target executable in the bin/ directory
TARGET = bin/hangman

# Default rule
all: build_dir bin_dir $(TARGET)

# Rule to link the object files and create the executable in bin/
$(TARGET): $(OBJ)
	$(CXX) $(CXXFLAGS) -o $(TARGET) $(OBJ)

# Rule to compile .cpp files into .o files in the build directory
build/%.o: src/%.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Create the build directory if it doesn't exist
build_dir:
	mkdir -p build

# Create the bin directory if it doesn't exist
bin_dir:
	mkdir -p bin

# Clean rule to remove object files and the executable
.PHONY: clean
clean:
	rm -f build/*.o bin/hangman
