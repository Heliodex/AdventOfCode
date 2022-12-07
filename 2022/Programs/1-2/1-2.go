package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
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

	largest := []int{} // change to array instead of number
	sum := 0

	for fileScanner.Scan() {
		txt := fileScanner.Text()
		if txt != "" {
			num, _ := strconv.Atoi(txt)
			sum += num
		} else {
			largest = append(largest, sum)
			sum = 0
		}
	}
	
	sort.Ints(largest)
	largest = largest[len(largest)-3:] // get largest 3 numbers
	total := 0
	for n := range largest {
		total += largest[n]
	}
	fmt.Println(total) // 200116
}
