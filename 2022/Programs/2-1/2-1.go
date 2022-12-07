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

	table := map[rune]int{
		'X': 1,
		'Y': 2,
		'Z': 3,
	}
	beats := map[rune]rune{
		'B': 'X',
		'C': 'Y',
		'A': 'Z',
		'X': 'C',
		'Y': 'A',
		'Z': 'B',
	}

	points := 0

	for fileScanner.Scan() {
		arr := strings.Split(fileScanner.Text(), " ")
		oppChoice := rune(arr[0][0])
		ourChoice := rune(arr[1][0])
		points += table[ourChoice]
		if beats[ourChoice] == oppChoice {
			points += 6
		} else if beats[oppChoice] != ourChoice {
			points += 3
		}
	}
	fmt.Println(points) // 10941
}
