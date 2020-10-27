package cn.jackbin.SimpleRecord.utils;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.util
 * @date: 2020/7/5 20:17
 **/
public class TestUtil {
    public static void main(String[] args) {
        int[] arr = {1,2,2,2,2,2};
        //int result = search(arr,2);
        int r = searchByRecursion(arr, 2,0,arr.length-1);
        System.out.println(r);
    }

    // 非递归的方式实现二分查找（找到最小的下标）
    public static int search(int[] arr,int n) {
        int low = 0;    // 查找的下标
        int high = arr.length-1;    // 查找的上标
        while(low <= high) {
            int mid = (low+high)/2;
            if(arr[mid]<n) {
                low=mid+1;
            } else if(arr[mid]>n) {
                high=mid-1;
            } else if(arr[mid]==n) {
                if (mid == low) {
                    // 此时返回mid标志
                    return mid;
                }
                // 如果相等则继续向前半部分寻找
                high=mid-1;
            }
        }
        return -1;
    }

    // 递归（）
    public static int searchByRecursion(int[] arr,int target,int begin,int end) {
        int mid=(begin+end)/2;
        if(target<arr[begin] || target>arr[end]) {
            return -1;
        }
        if(arr[mid] < target) {
            return searchByRecursion(arr,target,mid+1,end);
        } else if(arr[mid] > target) {
            return searchByRecursion(arr,target,begin,mid-1);
        } else {
            if (begin == mid) {
                return mid;
            }
            // 如果相等，则继续往前半部分寻找
            return searchByRecursion(arr,target,begin,mid-1);
        }
    }
}
