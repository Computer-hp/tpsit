#include <iostream>
#include <cctype>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include <filesystem>
#include <limits>
#include <string>
#include <vector>
#include <algorithm>
#include <time.h>
#include <stdlib.h>
#include "game.h"

static const std::filesystem::path program_running_path = std::filesystem::current_path();
static const std::string credentials_dir_name = "hangman_player_credentials";
static const std::string credentials_file_name = "credentials.txt";
static const std::string questions_dir_name = "questions";
static const std::string questions_file_name = "questions.txt";

Game::Game()
{
    player_has_won = false;

    set_lives(LIVES);
    score = 0;

    username = ""; 
    word_to_guess = ""; 
    typed_characters = ""; 

    set_words_to_guess();
}


void Game::set_score(int s) 
{
    score += s;

    if (score < 0)
        score = 0;
}


void Game::set_lives(int l)
{
    if (l < 0) lives = 0;

    else       lives = l;
}


/* 
    Extract's the words from the file questions.txt
    and sets them to the vector 'words_to_guess', so after the
    'chose_randomly_word_to_guess' method will
    chose from this vector a word that the 
    player will have to guess.
*/

void Game::set_words_to_guess()
{
    std::string questions_dir_path = program_running_path.parent_path().string() + '/' + questions_dir_name + '/' + questions_file_name;

    std::ifstream questions_file(questions_dir_path, std::ios::in);

    if (!questions_file)
    {
        std::cout << "\nError occured while creating credentials directory\n";
        return;
    }
    
    std::string word;

    while (questions_file >> word)
    {
        word.resize(word.length() - 1);
        words_to_guess.push_back(word);
    }

    questions_file.close();

    questions_file_size = words_to_guess.size();

    if (questions_file_size == 0)
    {
        std::cout << "Unable to determine file size." << std::endl;
        return;
    }
}



/*
    Starts the Hangman game.
*/

void Game::start()
{
    get_credentials_from_user_input();
    handle_credentials();
    menu();
}



/*
    Sets the username written in input by the user.
*/

void Game::get_credentials_from_user_input()
{
    std::string input_username;
    
    do
    {
        std::cout << "\n    Enter your name (max " << USERNAME_MAX_LENGTH << " characters without spaces): ";
        std::getline(std::cin, input_username);

        if (input_username.size() > 0 && !username_has_spaces_or_new_lines(input_username) &&
            input_username.length() <= USERNAME_MAX_LENGTH)
            
            break;
        
        else
            std::cout << "\n\tInvalid username.\n";
    }
    while (true);

    this->username = input_username;
}



/*
    Checks if the username has been written correctly.
    
    'return true' if username has spaces or new lines ("\n"),
    otherwise 'false'.
*/

bool Game::username_has_spaces_or_new_lines(const std::string& str)
{
    for (char c : str) 
    {
        if (c == ' ' || c == '\n')
            return true;
    }

    return false;
}



/*
    Creates the directory Hangman Credentials, if doesn't exist.
    Creates the file credentials.txt, if doesn't exist.
    Controls whether username exists (if credentials.txt exists)
    to save the last score of the player in the variable 'score'.
*/

void Game::handle_credentials()
{   
    if (!std::filesystem::exists(credentials_dir_name))
    {
        if (!std::filesystem::create_directory(credentials_dir_name))
        {
            std::cout << "\nError occured while creating credentials directory\n";
            return;
        }
    }

    if (!std::filesystem::exists(credentials_dir_name + "/" + credentials_file_name))
    {
        // TODO create the file inside the 'credentials_dir_name' directory
        std::ofstream credentials_file(credentials_dir_name + "/" + credentials_file_name);

        if (!credentials_file)
        {
            std::cout << "\nError occured while creating credentials file\n";
            return;
        }
        credentials_file.close();
    }

    std::fstream credentials_file(credentials_dir_name + "/" + credentials_file_name);

    if (!credentials_file)
    {
        std::cout << "\nError occured while opening the credentials file\n";
        return;
    }

    std::string line;

    while (std::getline(credentials_file, line)) // if username already exists
    {
        std::string first_word = line.substr(0, line.find_first_of(" "));

        if (first_word.compare(this->username) == 0)
        {
            size_t score_position = line.find_last_of(" ");

            set_score(std::stoi(line.substr(score_position + 1)));

            credentials_file.close();
            return;
        }
    }

    credentials_file.clear();
    credentials_file.seekp(0, std::ios::end);

    credentials_file << this->username << " score: " << this->score << std::endl;
    credentials_file.close();
}



void Game::menu()
{
    char input_character = menu_user_input();

    switch (input_character)
    {
    case 'p':
        play();
        break;

    case 's':
        see_score();
        break;

    case 'e':
        exit();
        break;

    default:
        break;
    }
}



/*
    Returns the character which is 
    used to start playing the game,
    to see the score or to exit the game.
*/

char Game::menu_user_input()
{
    char input_character;

    do
    {
        printf("\n\n\t\tPlay\t\tScore\t\tExit");
        printf("\nType:\n\t\t'p' to Play\t's' to see the Score\t'e' to Exit\n");

        input_character = get_character_from_input();

        if (input_character == 'p' ||
            input_character == 's' || 
            input_character == 'e')
            break;

    } while (true);

    return input_character;
}



/*
    Controls if the character taken 
    from the input is valid or not.
*/

char Game::get_character_from_input()
{
    char character_to_return;

    std::cin >> character_to_return;

    std::cin.clear();
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    if (std::cin.fail())
    {
        std::cerr << "Invalid input. Insert a character" << std::endl;
        return '\0';
    }

    if (std::isupper(character_to_return))
        std::tolower(character_to_return);

    return character_to_return;
}



/*
    Allows the user tu play the Hangman game.
*/

void Game::play()
{
    player_has_won = false;
    set_lives(LIVES);
    typed_characters = "";

    chose_randomly_word_to_guess();

    std::cout << "\n\nKey: " << word_to_guess << "\n\n";

    char guessed_characters[word_to_guess.length() + 1];

    for (long unsigned int i = 0; i < word_to_guess.length(); i++) // maybe in a better way
        guessed_characters[i] = '_';

    char input_character;

    while (true)
    {
        print_word_to_be_guessed(guessed_characters);
        print_characters_written_by_the_player(guessed_characters);

        if (lives <= 0 || player_has_won)
            break;
        
        std::cout << "\n\nType a letter or press '.' to exit the game: ";
        input_character = get_character_from_input();

        if (input_character == '.')
            break;

        if (word_to_guess.find(input_character) != std::string::npos)
        {
            if (typed_characters.find(input_character) != std::string::npos)
            {
                std::cout << "\n\n\t\t\tYou have already typed the character '" << input_character << "' !!!\n";
                continue;
            }


            std::cout << "\nCharacter '" << input_character << "' is correct\n";
            handle_guessed_character(guessed_characters, input_character);
        }
        else
        {
            std::cout << "\nCharacter '" << input_character << "' is wrong\n";
            set_lives(lives - 1);
        }   
    }

    if (input_character == '.')
        menu();

    else
        check_win_or_loss();
}



/*
    Sets the word that has to be guessed 
    in the game by chosing it randomly from 
    the vector  'words_to_guess'  which
    contains all the words from the file questions.txt.
*/

void Game::chose_randomly_word_to_guess()
{
    srand((unsigned) time(NULL));

    int random = rand() % questions_file_size;

    word_to_guess = words_to_guess[random];
}



/*
    Prints the word that the player has to guess.
    
    '_' for characters not guessed yet.
*/

void Game::print_word_to_be_guessed(const char guessed_characters[])
{
    std::cout << "\n\t\t";

    for (long unsigned int i = 0; i < word_to_guess.length(); i++)
        std::cout << guessed_characters[i] << " ";
}



/*
    Prints the characters that 
    the player has already written.
*/

void Game::print_characters_written_by_the_player(const char guessed_characters[])
{
    std::cout << "\t\t\t\t\tTyped characters: ";

    for (long unsigned int i = 0; i < typed_characters.length(); i++)
            std::cout << typed_characters[i] << " ";
}



/*
    Saves the character guessed by the player in the array of char
    'guessed_characters[]' so that print_word_to_be_guessed 
    shows the character/s guessed by the player in the console.
*/

void Game::handle_guessed_character(char guessed_characters[], char &input_character)
{
    for (long unsigned int i = 0; i < word_to_guess.length(); i++)
    {
        if (word_to_guess[i] == input_character)
            guessed_characters[i] = input_character;
    }

    typed_characters += input_character;

    check_victory(guessed_characters);
}



/* 
    Checks whether the player has guessed the word so 
    it can stop the while loop in the 'play()' method
    by setting the 'player_has_won' to 'true'.
*/

void Game::check_victory(const char *guessed_characters)
{
    while (*guessed_characters != '\0')
    {
        if (*guessed_characters == '_')
            return;

        guessed_characters++;
    }

    player_has_won = true;
}



/*
    Checks, after the method 'play()' has terminated,
    whether the player has won or not.
*/

void Game::check_win_or_loss()
{
    if (player_has_won)
    {
        set_score(word_to_guess.length());
        
        std::cout << "\n\n\n Player '" << this->username << "' has WON !!!";
    }
    else
    {
        set_score(-word_to_guess.length());
        std::cout << "\n\n\n Player '" << this->username << "' has LOST !!!";
    }

    write_new_score_in_credentials_file();

    std::cout << "\nCurrent score: " << this->score << std::endl;

    play();
    
}



/*
    Writes the new score of the player after playing 
    one or more round/s in the file 'credentials.txt'.
*/

void Game::write_new_score_in_credentials_file()
{
    std::fstream credentials_file(credentials_dir_name + "/" + credentials_file_name);

    if (!credentials_file)
    {
        std::cout << "\nError occured while opening the credentials file\n";
        return;
    }

    std::vector<std::string> all_credentials;
    std::string line;

    while (std::getline(credentials_file, line))
    {
        std::string first_word = line.substr(0, line.find_first_of(" "));

        if (first_word.compare(this->username) == 0)
        {
            size_t score_position = line.find_last_of(" ");
            std::string old_score = line.substr(score_position + 1);
            std::string modified_line = line.replace(score_position + 1, old_score.length(), std::to_string(this->score));
            
            line = modified_line;
        }

        all_credentials.push_back(line);
    }


    credentials_file.clear();
    credentials_file.seekp(0, std::ios::beg);

    for (const std::string& line : all_credentials)
    {
        credentials_file << line << std::endl;
    }

    credentials_file.close();
}



/*
    Shows the player's current score after typing
    the 's' character in the 'menu()' method.
*/

void Game::see_score()
{
    std::cout << '\n' << this->username << "  ----->  current score: " << this->score << std::endl;

    char input_character;

    do
    {
        std::cout << "\nPress 'e' to exit: ";
        
        input_character = get_character_from_input();

        if (input_character == 'e')
            break;
    } 
    while (true);
    
    menu();
}



/*
    Exits from the game.
*/

void Game::exit()
{
    std::exit(0);
}