package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
	"strconv"
)

func main() {
	file, err := os.Open("../../Inputs/4")
	if err != nil {
		fmt.Println("[error]:", err)
	}
	defer file.Close()
	fileScanner := bufio.NewScanner(file)
	fileScanner.Split(bufio.ScanLines)

	sum := 0

	for fileScanner.Scan() {
		arr := []string(strings.Split(fileScanner.Text(), ","))
		arr0 := strings.Split(arr[0], "-")
		arr1 := strings.Split(arr[1], "-")

		a1, _ := strconv.Atoi(arr0[0])
		a2, _ := strconv.Atoi(arr0[1])
		b1, _ := strconv.Atoi(arr1[0])
		b2, _ := strconv.Atoi(arr1[1])

		if a1 <= b1 && b2 <= a2 || b1 <= a1 && a2 <= b2 {
			sum++
		}
	}
	fmt.Println(sum) // 547
}
