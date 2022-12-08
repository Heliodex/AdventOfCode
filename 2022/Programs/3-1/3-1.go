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

	for fileScanner.Scan() {
		txt := fileScanner.Text()
		ln := len(txt) / 2
		txt1 := txt[ln:]
		txt2 := txt[:ln]

		out: // so nice to have..
		for _, c1 := range txt1 {
			for _, c2 := range txt2 {
				if c1 == c2 {
					prio := priority(c2)
					sum += prio
					break out // ..but it's just goto in disguise
				}
			}
		}
	}
	fmt.Println(sum) // 8515
}
