import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ArrayPractice {
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		ArrayPractice ap = new ArrayPractice();
		ap.practice1();
		ap.practice2();
		ap.practice3();
		ap.practice4();
		ap.practice5();
		ap.practice6();
		ap.practice7();
		ap.practice8();
		ap.practice9();
		ap.practice10();
	}

	public void practice1() {
		int[] array = new int[10];
		for(int i = 1; i <= array.length; i++){
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public void practice2() {
		int[] array = new int[10];
		for(int i = array.length; i >= 1; i--){
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public void practice3() {
		System.out.print("양의 정수 : ");
		int num = sc.nextInt();
		int[] array = new int[num];

		for(int i = 1; i <= array.length; i++){
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public void practice4() {
		String[] array = new String[5];
		array = new String[]{"사과", "귤", "포도", "복숭아", "참외"};
		System.out.println(array[1]);
	}

	public void practice5() {
		System.out.print("문자열 : ");
		String str = sc.next();
		System.out.print("문자 : ");
		char ch = sc.next().charAt(0);

		int count = 0;
		System.out.print("application에 i가 존재하는 위치(인덱스) :");
		for(int i = 0; i< str.length(); i++){
			if(str.charAt(i) == ch){
				System.out.print(i + " ");
				count++;
			}
		}
		System.out.println();
	}

	public void practice6() {
		System.out.print("정수 : ");
		int num = sc.nextInt();

		int[] array = new int[num];
		int sum = 0;
		for(int i = 0; i< array.length; i++){
			System.out.print("배열 " + i + "번째 인덱스에 넣을 값 : ");
			array[i] = sc.nextInt();
			sum += array[i];
		}

		System.out.println(Arrays.toString(array));
		System.out.println("총합 : "+ sum);
	}

	public void practice7() {
		int[] array = new int[10];
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < array.length; i++){
			array[i] = r.nextInt(10);

		}

		System.out.println(Arrays.toString(array));
	}

	public void practice8() {
		int[] array = new int[10];
		Random r = new Random(System.currentTimeMillis());
		int max = 0;
		int min = 0;

		for(int i = 0; i < array.length; i++){
			array[i] = r.nextInt(10);
			if(max < array[i]){
				max = array[i];
			}

			if(min > array[i]){
				min = array[i];
			}
		}

		System.out.println(Arrays.toString(array));
		System.out.println("최대값 : " + max);
		System.out.println("최솟값 : " + min);
	}

	public void practice9() {
		int[] array = new int[10];
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < array.length; i++){
			array[i] = r.nextInt(10) + 1;
			for(int j = 0; j<i; j++){
				if(array[i] == array[j]){
					i--;
				}
			}
		}

		System.out.println(Arrays.toString(array));
	}

	public void practice10() {
		int[] array = new int[6];
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < array.length; i++){
			array[i] = r.nextInt(1,45);
			for(int j = 0; j<i; j++){
				if(array[i] == array[j]){
					i--;
				}
			}
		}

		Arrays.sort(array);
		System.out.println(Arrays.toString(array));
	}

}