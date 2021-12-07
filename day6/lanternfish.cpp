#include <iostream>
#include <fstream>
#include <string>
#include <list>

namespace constants
{
    static constexpr int SIMULATION_STEPS_1 = 80;
    static constexpr int SIMULATION_STEPS_2 = 256;
}

static std::uint_fast64_t *simulate_once(std::uint_fast64_t buckets[])
{
    // initialize new buckets
    std::uint_fast64_t *new_buckets = new std::uint_fast64_t[9];
    for (int i = 0; i < 9; i++)
    {
        new_buckets[i] = 0;
    }

    // spawn
    new_buckets[8] += buckets[0];
    new_buckets[6] += buckets[0];
    // mature or regenerate
    for (int i = 1; i < 9; i++)
    {
        new_buckets[i - 1] += buckets[i];
    }

    // free and return
    delete[] buckets;
    return new_buckets;
}

static std::size_t simulate_steps(std::list<int> input, int steps)
{
    // initialize buckets
    std::size_t *buckets = new std::size_t[9];
    for (int i = 0; i < 9; i++)
    {
        buckets[i] = 0;
    }

    // fill with starting values
    for (auto n : input)
    {
        buckets[n]++;
    }

    // simulate
    for (int i = 0; i < steps; i++)
    {
        buckets = simulate_once(buckets);
    }

    // sum all fish in each bucket
    std::size_t result = 0;
    for (int i = 0; i < 9; i++)
    {
        result += buckets[i];
    }

    // free and return
    delete[] buckets;
    return result;
}

// "1,3,3,7" -> [1,3,3,7]
static std::list<int> parse_input(std::string text)
{
    std::list<int> res;

    for (std::size_t i = 0; i < text.length() - 1; i += 2)
    {
        res.push_back(text.at(i) - '0');
    }

    return res;
}

static std::size_t puzzle_one(std::list<int> input)
{
    return simulate_steps(input, constants::SIMULATION_STEPS_1);
}

static std::size_t puzzle_two(std::list<int> input)
{
    return simulate_steps(input, constants::SIMULATION_STEPS_2);
}

int main(void)
{
    // read input file
    std::ifstream file("input");
    std::string input((std::istreambuf_iterator<char>(file)),
                      std::istreambuf_iterator<char>());
    file.close();

    // parse
    auto parsed = parse_input(input);

    // calculate
    std::cout << puzzle_one(parsed) << std::endl;
    std::cout << puzzle_two(parsed) << std::endl;

    return 0;
}