export type ScreenType = 'home' | 'add-food' | 'confirm' | 'diary' | 'progress' | 'profile';

export type MealType = 'BREAKFAST' | 'LUNCH' | 'SNACK' | 'DINNER';

export interface FoodItem {
  id: string | number;
  name: string;
  icon: string;
  qty: number;
  unit: string;
  kcalPerUnit: number;
  protein: number;
  carbs: number;
  fat: number;
}

export interface DiaryEntry {
  id: string;
  name: string;
  portion: string;
  calories: number;
}

export interface UserProfile {
  name: string;
  age: number;
  height: string;
  weight: number;
  goalWeight: number;
  goal: string;
  activityLevel: string;
  dailyGoal: number;
  isBangla: boolean;
  notifications: boolean;
  darkMode: boolean;
}
