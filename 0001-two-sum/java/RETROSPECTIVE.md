# 풀이 회고

## 핵심 아이디어

배열을 한 번 순회하면서, 현재 원소와 쌍을 이루어 `target`이 될 "보완값(complement)"을 먼저 계산합니다. 해시맵에 이미 그 보완값이 저장되어 있다면, 이전에 지나친 어떤 원소와 현재 원소가 정답 쌍임을 의미합니다. 이처럼 "지금까지 본 값들"을 해시맵에 누적하면서 정답을 즉시 확인할 수 있기 때문에, 배열을 두 번 중첩 순회하지 않아도 됩니다. 결국 **"찾기"와 "저장"을 동시에 수행하는 단일 패스** 전략이 이 풀이의 본질입니다.

## 사용된 자료구조 / 알고리즘

- **해시맵 (HashMap)**: 값(value) → 인덱스(index) 매핑, O(1) 평균 조회
- **단일 패스 탐색 (One-pass Traversal)**: 배열을 한 번만 순회하여 탐색과 저장을 동시에 처리

## 복잡도 분석

- **시간 복잡도**: O(n)
  - 배열을 한 번만 순회하며, 각 원소마다 해시맵 조회·삽입이 평균 O(1)이므로 전체 O(n)
- **공간 복잡도**: O(n)
  - 최악의 경우 배열의 모든 원소를 해시맵에 저장하므로 O(n) 추가 공간 필요

## 개선된 코드

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int[] twoSum(int[] nums, int target) {
        // 지금까지 순회한 원소의 값 → 인덱스를 기록하는 해시맵
        Map<Integer, Integer> visitedValueToIndex = new HashMap<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            int currentValue = nums[currentIndex];

            // target을 만들기 위해 현재 값과 쌍을 이루어야 하는 보완값 계산
            int complementValue = target - currentValue;

            // 보완값이 이미 해시맵에 존재하면 두 인덱스가 정답
            if (visitedValueToIndex.containsKey(complementValue)) {
                int complementIndex = visitedValueToIndex.get(complementValue);
                return new int[] { complementIndex, currentIndex };
            }

            // 현재 값과 인덱스를 해시맵에 기록하여 이후 순회에서 참조 가능하게 함
            visitedValueToIndex.put(currentValue, currentIndex);
        }

        // 문제 조건상 유효한 정답이 반드시 존재하므로 이 지점에 도달하지 않음
        throw new IllegalArgumentException("유효한 Two Sum 정답이 존재하지 않습니다.");
    }
}
```

## 다른 접근

**① 브루트 포스 (Brute Force) - O(n²) / O(1)**
모든 원소 쌍 `(i, j)`를 이중 반복문으로 검사하여 `nums[i] + nums[j] == target`인 경우를 찾습니다. 추가 공간이 전혀 필요 없다는 장점이 있지만, 입력 크기가 커질수록 시간이 제곱으로 증가해 `n = 10,000` 규모에서는 비효율적입니다.

**② 정렬 + 투 포인터 (Two Pointers) - O(n log n) / O(n)**
배열을 정렬한 뒤 양 끝에서 포인터를 좁혀가며 합이 `target`인 쌍을 찾습니다. 시간 복잡도는 정렬 비용인 O(n log n)으로, 현재 풀이보다 느립니다. 또한 정렬 과정에서 원래 인덱스 정보가 사라지므로, **원본 인덱스를 보존하기 위한 별도의 인덱스 배열이 추가로 필요**합니다. 결과적으로 공간도 절약되지 않아 이 문제에서는 트레이드오프가 좋지 않습니다.

## 비슷한 문제

- **LeetCode 167** - Two Sum II (Input Array Is Sorted): 정렬된 배열에서 투 포인터로 Two Sum을 푸는 변형
- **LeetCode 15** - 3Sum: 세 수의 합이 0이 되는 모든 조합을 찾는 확장 문제
- **LeetCode 560** - Subarray Sum Equals K: 해시맵으로 누적합을 관리하여 조건을 만족하는 부분 배열을 찾는 유사 패턴