import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 값과 해당 인덱스를 저장할 해시맵 생성
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            // target을 만들기 위해 필요한 나머지 값 계산
            int complement = target - nums[i];
            
            // 그 값이 이미 맵에 있다면 정답 반환
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            
            // 현재 값과 인덱스를 맵에 저장
            map.put(nums[i], i);
        }
        
        // 문제 조건상 항상 정답이 존재하므로 이 줄은 실행되지 않음
        throw new IllegalArgumentException("No two sum solution");
    }
}
