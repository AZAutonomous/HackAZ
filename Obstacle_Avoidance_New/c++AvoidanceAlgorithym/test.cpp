#include<iostream>
#include<vector>
std::vector<int> swap(int a, int b) {
	int arr[] = { b, a };
	std::vector<int> out(arr, arr + 2);
	return out;
}

int main(void) {
	int a = 1, b = 2;
	std::vector<int> arr	= swap(a, b);
	std::cout << arr[0] << ' ' << arr[1];
	std::cout << '\n' <<  arr[0];
}