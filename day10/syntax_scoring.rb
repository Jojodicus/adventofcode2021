def malformed_score(line)
    para_stack = []

    line.each_char do |char|
        # push
        if char == '(' || char == '[' || char == '{' || char == '<'
            para_stack << char
        else
            # pop
            # malformed?
            if char == ')' && para_stack.pop != '('
                return 3
            elsif char == ']' && para_stack.pop != '['
                return 57
            elsif char == '}' && para_stack.pop != '{'
                return 1197 
            elsif char == '>' && para_stack.pop != '<'
                return 25137
            end
        end
    end

    # not malformed
    return 0;
end

def incomplete_closing(line)
    para_stack = []

    line.each_char do |char|
        # push
        if char == '(' || char == '[' || char == '{' || char == '<'
            para_stack << char
        else
            # pop
            # malformed?
            if char == ')' && para_stack.pop != '('
                return []
            elsif char == ']' && para_stack.pop != '['
                return []
            elsif char == '}' && para_stack.pop != '{'
                return [] 
            elsif char == '>' && para_stack.pop != '<'
                return []
            end
        end
    end

    # not empty if incomplete
    return para_stack
end

def incomplete_score(fixable)
    score = 0

    fixable.reverse.each do |para|
        score *= 5

        # add score of fixing
        if para == '('
            score += 1
        elsif para == '['
            score += 2
        elsif para == '{'
            score += 3
        elsif para == '<'
            score += 4
        end
    end

    return score
end

# open input file
input = File.open("input", "r")
input_lines = input.readlines
input.close

# puzzle 1
score = 0
for line in input_lines
    score += malformed_score(line)
end
puts score

# puzzle 2
scores = []
for line in input_lines
    current = incomplete_score(incomplete_closing(line))
    if current != 0
        scores << current
    end
end
puts scores.sort[scores.length/2]