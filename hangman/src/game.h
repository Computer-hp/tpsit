#pragma once

#include <vector>

#define LIVES                 3
#define USERNAME_MAX_LENGTH  14




class Game
{
public:

    Game();

    void set_score(int s);
    void set_lives(int l);

    void start();
    void get_credentials_from_user_input();
    bool username_has_spaces_or_new_lines(const std::string& str);
    void handle_credentials();
    
    void menu();
    char menu_user_input();

    char get_character_from_input();
    
    void play();
    void set_words_to_guess();
    void chose_randomly_word_to_guess();
    void handle_guessed_character(char guessed_characters[], char &input_character);
    void print_word_to_be_guessed(const char guessed_characters[]);
    void print_characters_written_by_the_player(const char guessed_characters[]);
    void check_victory(const char* guessed_characters);
    void check_win_or_loss();
    void write_new_score_in_credentials_file();

    void see_score();
    void exit();


private:
    
    bool player_has_won;
    int lives;
    int score;
    int questions_file_size;
    std::string username;
    std::string word_to_guess;
    std::string typed_characters;
    std::vector<std::string> words_to_guess;
};