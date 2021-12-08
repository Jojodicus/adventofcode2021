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
    return "TODO";
}

function solve(input) {
    let data = input.split("\r\n");
    for (let i = 0; i < data.length; i++) {
        data[i] = data[i].split(" | ");
        data[i][0] = data[i][0].split(" ");
        data[i][1] = data[i][1].split(" ");
    }

    document.getElementById("p1").innerHTML += puzzleOne(data);
}