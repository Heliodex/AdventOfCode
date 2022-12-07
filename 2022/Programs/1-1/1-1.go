package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func main() {
	file, err := os.Open("../../Inputs/1")
	if err != nil {
		fmt.Println("[error]:", err)
	}
	defer file.Close()
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)

	// first time really writing any go code, wish me luck

	largest := 0
	sum := 0

	for fileScanner.Scan() {
		txt := fileScanner.Text()
		if txt != "" {
			num, _ := strconv.Atoi(txt)
			sum += num
		} else {
			if sum > largest {
				largest = sum
			}
			sum = 0
		}
	}
	fmt.Println(largest) // 67016
}
