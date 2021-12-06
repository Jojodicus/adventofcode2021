#include <iostream>
#include <filesystem>
#include <fstream>
#include <string>
#include <vector>
#include <list>
#include <bits/stdc++.h>

//TODO: includes

static const std::string DELIMITER = ",";
static constexpr int NEW_FISH_COOLDOWN = 8;
static constexpr int OLD_FISH_COOLDOWN = 6;

std::list<int> parse_input(std::string text);

// "1,3,3,7" -> [1,3,3,7]
std::list<int> parse_input(std::string text) {
    std::list<int> res;
    
    int start = 0;
    int end = text.find(DELIMITER);

    while (end != -1) {
        res.push_back(std::stoi(text.substr(start, end-start)));

        start = end + DELIMITER.size();
        end = text.find(DELIMITER, start);    
    }
    res.push_back(std::stoi(text.substr(start, end-start)));

    return res;
}

int main(void) {
    // open input file
    std::ifstream file("input");
    std::string input((std::istreambuf_iterator<char>(file)),
                          std::istreambuf_iterator<char>());
    file.close();

    // parse input
    auto parsed_input = parse_input(input);

    // print input
    for (auto i : parsed_input) {
        std::cout << i << std::endl;
    }
    
    //TODO puzzles

    return 0;
}