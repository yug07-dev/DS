using namespace std;
#include <cstdlib>
#include <iostream>
#include <omp.h>
#include <vector>

int main() {
    int N = 1000;
    int num_threads = 4;

    int sum = 0;
    int chunk_size = N / num_threads;

    vector<int> partial_sums(num_threads, 0);
    vector<int> arr(N);

    for (int i = 0; i < N; i++) {
        arr[i] = rand() % 100;
    }

    #pragma omp parallel num_threads(num_threads)
    {
        int thread_num = omp_get_thread_num();

        int start = thread_num * chunk_size;
        int end = (thread_num == num_threads - 1) ? N : start + chunk_size;

        int partial_sum = 0;

        for (int i = start; i < end; i++) {
            partial_sum += arr[i];
        }

        partial_sums[thread_num] = partial_sum;
    }

    // Print partial sums and compute final sum
    for (int i = 0; i < num_threads; i++) {
        cout << "Partial sum calculated by thread " << i
             << ": " << partial_sums[i] << endl;

        sum += partial_sums[i];
    }

    cout << "Total sum: " << sum << endl;

    return 0;
}