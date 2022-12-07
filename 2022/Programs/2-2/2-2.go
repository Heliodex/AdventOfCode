package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	file, err := os.Open("../../Inputs/2")
	if err != nil {
		fmt.Println("[error]:", err)
	}
	defer file.Close()
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)

	table := map[string]int{
		"X": 1,
		"Y": 2,
		"Z": 3,
	}
	beats := map[string]string{
		"B": "X",
		"C": "Y",
		"A": "Z",
		"X": "C",
		"Y": "A",
		"Z": "B",
	}
	ldw := map[string][3]string{ // whether to lose, draw, or win
		"A": {"Z", "X", "Y"}, // [0]: lose, [1]: draw, [2]: win
		"B": {"X", "Y", "Z"},
		"C": {"Y", "Z", "X"},
	}

	points := 0

	for fileScanner.Scan() {
		arr := strings.Split(fileScanner.Text(), " ")

		oppChoice := arr[0]
		ourChoice := ldw[oppChoice][table[arr[1]] - 1]
		points += table[ourChoice]
		
		if oppChoice == beats[ourChoice] {
			points += 6
		} else if  ourChoice != beats[oppChoice] {
			points += 3
		}
	}
	fmt.Println(points) // 13071
}
