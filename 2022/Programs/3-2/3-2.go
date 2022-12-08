package main

import (
	"bufio"
	"fmt"
	"os"
	"unicode"
)

func priority(c rune) int {
	if unicode.IsUpper(c) {
		return int(c) - 38
	}
	return int(c) - 96
}

func main() {
	file, err := os.Open("../../Inputs/3")
	if err != nil {
		fmt.Println("[error]:", err)
	}
	defer file.Close()
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)

	sum := 0
	line := 0
	group := [3]string{}

	for fileScanner.Scan() {
		txt := fileScanner.Text()
		group[line] = txt
		line++

		if line == 3 {
			out:
			for _, c1 := range group[0] { // i
				for _, c2 := range group[1] { // want
					for _, c3 := range group[2] { // die
						if c1 == c2 && c2 == c3 {
							sum += priority(c3)
							fmt.Println(string(c3))
							break out
						}
					}
				}
			}
			line = 0
			group = [3]string{}
		}
	}
	fmt.Println(sum) // 2434
}
