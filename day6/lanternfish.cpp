#include <iostream>
#include <filesystem>
#include <fstream>
#include <string>
#include <vector>
#include <list>
#include <bits/stdc++.h>

#include <chrono>

// TODO: includes
namespace constants
{
    static const std::string DELIMITER = ",";
    static constexpr int NEW_FISH_COOLDOWN = 8;
    static constexpr int OLD_FISH_COOLDOWN = 6;
    static constexpr int SIMULATION_STEPS_1 = 80;
    static constexpr int SIMULATION_STEPS_2 = 256;
}

static std::list<int> parse_input(std::string text);
static std::size_t puzzle_one(std::list<int> input);
static std::size_t puzzle_two(std::list<int> input);
static std::list<int> simulate_once(std::list<int> fish);

static void print_list(std::list<int> l); // TODO: remove

static std::size_t puzzle_one(std::list<int> input)
{
    // simulate constants::SIMULATION_STEPS_1 times
    for (int i = 0; i < constants::SIMULATION_STEPS_1; i++)
    {
        input = simulate_once(input);
    }

    return input.size();
}

static std::size_t puzzle_two(std::list<int> input)
{
    // simulate constants::SIMULATION_STEPS_2 times
    for (int i = 0; i < constants::SIMULATION_STEPS_2; i++)
    {
        input = simulate_once(input);
    }
    std::cout << std::endl;

    return input.size();
}

static std::list<int> simulate_once(std::list<int> fish)
{
    std::list<int> spawn;
    uint_fast32_t c = 0;

    // loop over fish
    for (auto f : fish)
    {
        f--;
        if (f < 0) // spawning
        {
            f = constants::OLD_FISH_COOLDOWN;
            c++; // hehehe
        }
        spawn.push_back(f);
    }

    // add spawn
    for (; c > 0; c--)
    {
        spawn.push_back(constants::NEW_FISH_COOLDOWN);
    }

    return spawn;
}

static void print_list(std::list<int> l) // TODO: remove
{
    for (auto e : l)
    {
        std::cout << e << ", ";
    }
    std::cout << std::endl;
}

// "1,3,3,7" -> [1,3,3,7]
static std::list<int> parse_input(std::string text)
{
    std::list<int> res;

    // yes this accepts more than one digit
    std::size_t start = 0;
    std::size_t end = text.find(constants::DELIMITER);

    while (end != -1)
    {
        res.push_back(std::stoi(text.substr(start, end - start)));

        start = end + constants::DELIMITER.size();
        end = text.find(constants::DELIMITER, start);
    }
    // last number
    res.push_back(std::stoi(text.substr(start, end - start)));

    return res;
}

int main(void)
{
    // read input file
    std::ifstream file("input");
    std::string input((std::istreambuf_iterator<char>(file)),
                      std::istreambuf_iterator<char>());
    file.close();

    auto parsed = parse_input(input);

//     using std::chrono::high_resolution_clock;
//     using std::chrono::duration_cast;
//     using std::chrono::duration;
//     using std::chrono::microseconds;
//     auto t1 = high_resolution_clock::now();
//     long_operation();
//     auto t2 = high_resolution_clock::now();
//     duration<double, std::micro> us_double = t2 - t1;
//     std::cout << us_double.count() << "ms";

    std::cout << puzzle_one(parsed) << std::endl;
    std::cout << puzzle_two(parsed) << std::endl;

    return 0;
}