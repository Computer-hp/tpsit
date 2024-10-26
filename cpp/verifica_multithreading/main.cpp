#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <atomic>
#include <fstream>
#include <vector>
#include <stdlib.h>
#include <time.h>


#define TIME_VAL 100
#define N_CONSUMER_THREAD 4
#define MAX_RUNNERS 20
#define MAX_SECONDS 10000
#define MAX_DISTANCE 400
#define MAX_SPEED 100

using namespace std;


typedef struct
{
    int numero_corridore; // da 1 a 20
    int tempo_partenza;
    float distanza_inizio;
    float velocita;

} Data;



Data generate_random_data();
void base();  // 1 thread che genera in modo random i dati e li carica nel vector
void read(int n_corridore);  // 4 thread che leggono i dati dei corridori italiani dal vector (da 10 a 13)

void print_corridore(Data *data, ofstream &out_file);


vector<Data> v;
mutex critical;
condition_variable_any vector_avaible;
bool is_vector_occupied = false;

atomic<int> working_threads = N_CONSUMER_THREAD + 1;
atomic<bool> program_is_running = true;


int main()
{
    srand(time(NULL));

    thread base_thread(base);

    this_thread::sleep_for(chrono::seconds(2));

    thread consumer_threads[N_CONSUMER_THREAD];

    for (int i = 0; i < N_CONSUMER_THREAD; i++)
        consumer_threads[i] = thread(read, i + 10);

    char input;

    cout << "\nPress a key and enter to terminate the execution ";
    cin >> input;

    program_is_running = false;

    while (working_threads > 0);

    for (int i = 0; i < N_CONSUMER_THREAD; i++)
        consumer_threads[i].join();

    base_thread.join();

    return 0;
}


void base()
{
    while (program_is_running)
    {
        this_thread::sleep_for(chrono::seconds(TIME_VAL));

        Data tmp_data = generate_random_data();

        {
            lock_guard<mutex> lock(critical);

            if (is_vector_occupied)
                vector_avaible.wait(critical);

            is_vector_occupied = true;

            v.push_back(tmp_data);

            is_vector_occupied = false;
        }

        vector_avaible.notify_one();
    }

    working_threads--;
}


Data generate_random_data()
{
    Data data =
    {
        .numero_corridore = rand() % MAX_RUNNERS + 1,
        .tempo_partenza = rand() % MAX_SECONDS,
        .distanza_inizio = (float)rand() / ((float)RAND_MAX / (MAX_DISTANCE)),
        .velocita = (float)rand() / ((float)RAND_MAX / (MAX_SPEED))
    };

    return data;
}


void read(int n_corridore)
{
    int i = 0;

    const string file_name = "Ita" + to_string(n_corridore) + ".txt";

    ofstream out_file(file_name);

    if (!out_file)
    {
        cout << "\nError occured while opening file " << file_name << '\n';
        return;
    }

    while (program_is_running)
    {
        this_thread::sleep_for(chrono::milliseconds(TIME_VAL));

        {
            lock_guard<mutex> lock(critical);

            if (is_vector_occupied)
                vector_avaible.wait(critical);

            is_vector_occupied = true;

            if (i < v.size() && v[i].numero_corridore == n_corridore)
                print_corridore(&v[i], out_file);

            if (i == v.size() - 1) i = 0;
            else i++;

            is_vector_occupied = false;
        }

        vector_avaible.notify_one();
    }

    out_file.close();
    working_threads--;
}


void print_corridore(Data *data, ofstream &out_file)
{
    out_file << "\nnumero_corridore = " << data -> numero_corridore << '\n'
         << "tempo_partenza = " << data -> tempo_partenza << " s\n"
         << "distanza_inizio = " << data -> distanza_inizio << " km\n"
         << "velocita = " << data -> velocita << " km / h\n";
}