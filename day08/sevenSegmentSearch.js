const input = document.querySelector("#fileInput");

input.addEventListener("change", () => {
    const file = input.files.item(0);
    fileToText(file, (text) => {
        solve(text);
    });
});

function fileToText(file, callback) {
    const reader = new FileReader();
    reader.readAsText(file);
    reader.onload = () => {
        callback(reader.result);
    };
}

function puzzleOne(data) {
    let result = 0;

    for (let i = 0; i < data.length; i++) {
        for (let j = 0; j < data[i][1].length; j++) {
            var len = data[i][1][j].length;
            if (len <= 4 || len == 7) {
                result += 1;
            }
        }
    }

    return result;
}

function puzzleTwo(data) {
    let result = 0;

    for (let i = 0; i < data.length; i++) {
        result += decode(data[i]);
    }

    return result;
}

function decode(data) {
    let input = data[0];

    // decode input
    let coded = new Array(10).fill(""); // digits as they are encoded in the input
    let segments = new Array(7).fill(""); // segments as they are encoded in the input

    // numbers 1, 4, 7, 8
    for (let i = 0; i < input.length; i++) {
        switch (input[i].length) {
            case 2:
                coded[1] = input[i];
                break;
            case 3:
                coded[7] = input[i];
                break;
            case 4:
                coded[4] = input[i];
                break;
            case 7:
                coded[8] = input[i];
                break;
        }
    }

    // segment a
    // 1 and 7 -> a
    for (let i = 0; i < 3; i++) {
        if (!(coded[1].includes(coded[7].charAt(i)))) {
            segments[0] = coded[7].charAt(i);
            break;
        }
    }

    // segment g, number 9
    // 4 and 9 and a -> g
    for (let i = 0; i < input.length; i++) {
        var curr = input[i];

        if (curr.length != 6) {
            continue;
        } // only 0, 6, 9 remain

        // check if all segments of 4 and a are included
        var remaining = "";
        for (let j = 0; j < curr.length; j++) {
            var cchar = curr.charAt(j);
            if (cchar == segments[0] || coded[4].includes(cchar)) {
                continue;
            }
            // neither in a or 4
            remaining += cchar;
        }

        // only one extra char -> g in 9
        if (remaining.length == 1) {
            coded[9] = curr;
            segments[6] = remaining;
            break;
        }
    }

    // segment e
    // in 8 but not in 9 -> e
    for (let i = 0; i < coded[8].length; i++) {
        if (!coded[9].includes(coded[8].charAt(i))) {
            segments[4] = coded[8].charAt(i);
            break;
        }
    }

    // segment b, number 0
    // not in 1, nor a/e/g -> b
    for (let i = 0; i < input.length; i++) {
        var curr = input[i];
        
        if (curr.length != 6 || curr == coded[9]) {
            continue;
        } // only 0, 6 remain

        // check if all segments of 1, a, e, g are included
        var remaining = "";
        for (let j = 0; j < curr.length; j++) {
            var cchar = curr.charAt(j);
            if (cchar == segments[0] || cchar == segments[4] || cchar == segments[6] || coded[1].includes(cchar)) {
                continue;
            }
            // neither in a or 4
            remaining += cchar;
        }

        // only one extra char -> b in 0
        if (remaining.length == 1) {
            coded[0] = curr;
            segments[2] = remaining;
            break;
        }
    }

    // number 6
    // length 6 but neiter 0 nor 9 -> 6
    for (let i = 0; i < input.length; i++) {
        var curr = input[i];

        if (curr.length != 6 || curr == coded[0] || curr == coded[9]) {
            continue;
        } // only 6 remains

        coded[6] = curr;
    }

    // number 2
    // length 5 and e -> 2
    for (let i = 0; i < input.length; i++) {
        var curr = input[i];

        if (curr.length == 5 && curr.includes(segments[4])) {
            coded[2] = curr;
            break;
        }
    }

    // number 3
    // length 5 and contains 1
    for (let i = 0; i < input.length; i++) {
        var curr = input[i];

        if (curr.length == 5 && curr.includes(coded[1].charAt(0)) && curr.includes(coded[1].charAt(1))) {
            coded[3] = curr;
            break;
        }
    }

    // number 5
    // final number
    for (let i = 0; i < input.length; i++) {
        var curr = input[i];

        if (curr.length == 5 && curr != coded[3] && curr != coded[2]) {
            coded[5] = curr;
            break;
        }
    }

    // prepare coded digits
    for (let i = 0; i < coded.length; i++) {
        coded[i] = coded[i].split("").sort().join("");
    }

    
    // decode result
    let result = 0;
    let output = data[1];
    for (let i = 0; i < output.length; i++) {
        result *= 10;
        var curr = output[i].split("").sort().join("");

        for (let j = 0; j < coded.length; j++) {
            if (curr == coded[j]) {
                result += j;
                break;
            }
        }
    }
    
    
    return result;
}

function solve(input) {
    let data = input.split("\r\n");
    for (let i = 0; i < data.length; i++) {
        data[i] = data[i].split(" | ");
        data[i][0] = data[i][0].split(" ");
        data[i][1] = data[i][1].split(" ");
    }

    document.getElementById("p1r").innerHTML = puzzleOne(data);
    document.getElementById("p2r").innerHTML = puzzleTwo(data);
}
