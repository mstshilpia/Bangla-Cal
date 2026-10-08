import { useState } from 'react';
import { FoodItem, ScreenType, UserProfile } from './types';
import { 
  Flame, 
  Home, 
  BookOpen, 
  TrendingUp, 
  User, 
  Plus, 
  Minus, 
  Trash2, 
  Sparkles, 
  ArrowLeft, 
  Check, 
  Info
} from 'lucide-react';

export default function App() {
  const [currentScreen, setCurrentScreen] = useState<ScreenType>('home');
  const [selectedMeal, setSelectedMeal] = useState<string>('LUNCH');
  
  // App Calorie & Macro State
  const [consumed, setConsumed] = useState(1250);
  const [goal] = useState(2000);
  const [protein, setProtein] = useState(65);
  const [carbs, setCarbs] = useState(160);
  const [fat, setFat] = useState(40);

  // Natural Input State
  const [naturalInput, setNaturalInput] = useState('দুপুরে ২ প্লেট ভাত, ১ পিস রুই মাছ আর ডাল খেয়েছি');
  const [isAnalyzing, setIsAnalyzing] = useState(false);
  const [hasAnalyzed, setHasAnalyzed] = useState(false);

  // Food Items for Confirmation Screen
  const [foodItems, setFoodItems] = useState<FoodItem[]>([
    { id: 1, name: 'ভাত', icon: '🍚', qty: 2, unit: 'প্লেট', kcalPerUnit: 200, protein: 4, carbs: 44, fat: 1 },
    { id: 2, name: 'রুই মাছ', icon: '🐟', qty: 1, unit: 'পিস', kcalPerUnit: 150, protein: 18, carbs: 0, fat: 8 },
    { id: 3, name: 'ডাল', icon: '🥣', qty: 1, unit: 'বাটি', kcalPerUnit: 70, protein: 6, carbs: 10, fat: 2 }
  ]);

  // Profile State
  const [profile, setProfile] = useState<UserProfile>({
    name: 'Ramim',
    age: 21,
    height: "5'6\"",
    weight: 65,
    goalWeight: 60,
    goal: 'Lose weight',
    activityLevel: 'Moderately active',
    dailyGoal: 2000,
    isBangla: true,
    notifications: true,
    darkMode: false
  });

  const [toastMessage, setToastMessage] = useState<string | null>(null);

  const showToast = (msg: string) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(null), 3000);
  };

  const remaining = Math.max(0, goal - consumed);
  const progressRatio = Math.min(1, consumed / goal);
  const dashOffset = 264 - 264 * progressRatio;

  // Confirmation screen calculated totals
  const totalKcal = foodItems.reduce((acc, f) => acc + f.qty * f.kcalPerUnit, 0);
  const totalProtein = foodItems.reduce((acc, f) => acc + f.qty * f.protein, 0);
  const totalCarbs = foodItems.reduce((acc, f) => acc + f.qty * f.carbs, 0);
  const totalFat = foodItems.reduce((acc, f) => acc + f.qty * f.fat, 0);

  const updateQuantity = (id: string | number, delta: number) => {
    setFoodItems(prev => prev.map(item => {
      if (item.id === id) {
        return { ...item, qty: Math.max(1, item.qty + delta) };
      }
      return item;
    }));
  };

  const removeItem = (id: string | number) => {
    setFoodItems(prev => prev.filter(item => item.id !== id));
  };

  const handleAnalyze = () => {
    setIsAnalyzing(true);
    setHasAnalyzed(false);
    setTimeout(() => {
      setIsAnalyzing(false);
      setHasAnalyzed(true);
    }, 450);
  };

  const saveMeal = (addedKcal: number) => {
    setConsumed(prev => prev + addedKcal);
    setProtein(prev => prev + 28);
    setCarbs(prev => prev + 82);
    setFat(prev => prev + 18);
    showToast(`✅ খাবার সফলভাবে যোগ হয়েছে (~${addedKcal} kcal)!`);
    setCurrentScreen('diary');
  };

  return (
    <div className="w-full max-w-md mx-auto min-h-screen bg-white shadow-xl flex flex-col relative pb-20 border-x border-gray-100">
      
      {/* Toast Notification */}
      {toastMessage && (
        <div className="fixed top-4 left-1/2 -translate-x-1/2 z-50 bg-gray-900 text-white px-4 py-2.5 rounded-full text-xs font-semibold shadow-lg animate-bounce">
          {toastMessage}
        </div>
      )}

      {/* Top Header */}
      <header className="px-5 pt-6 pb-3 bg-white sticky top-0 z-20 border-b border-gray-100 flex items-center justify-between">
        <div>
          <h1 className="text-xl font-bold text-brand-500 tracking-tight flex items-center gap-1.5">
            <span>🌿</span> BanglaCal
          </h1>
          <p className="text-xs text-gray-500">বাংলা ক্যালোরি ট্র্যাকার</p>
        </div>
        <button 
          onClick={() => setProfile(p => ({ ...p, isBangla: !p.isBangla }))}
          className="text-xs px-2.5 py-1 rounded-full bg-brand-50 text-brand-600 font-semibold border border-brand-100"
        >
          {profile.isBangla ? 'বাংলা / EN' : 'EN / বাংলা'}
        </button>
      </header>

      {/* Main Content Area */}
      <main className="flex-1 p-4 overflow-y-auto no-scrollbar">
        
        {/* ================= 1. HOME SCREEN ================= */}
        {currentScreen === 'home' && (
          <div className="space-y-4">
            {/* Greeting */}
            <div className="flex items-center justify-between">
              <div>
                <h2 className="text-lg font-bold text-gray-900">Good morning 👋</h2>
                <p className="text-sm font-semibold text-brand-500">আজকের খাবারের হিসাব</p>
              </div>
              <span className="text-xs font-semibold px-2.5 py-1 bg-green-50 text-brand-600 rounded-full border border-green-200">
                আজ, ৮ অক্টোবর
              </span>
            </div>

            {/* Circular Calorie Card */}
            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-5 shadow-sm">
              <div className="flex items-center justify-between mb-4">
                <span className="text-sm font-bold text-gray-800 flex items-center gap-1.5">
                  <Flame className="w-4 h-4 text-flame-500" /> দৈনিক ক্যালোরি অগ্রগতি
                </span>
                <span className="text-xs font-bold text-flame-600 bg-orange-50 px-2 py-0.5 rounded-full">
                  লক্ষ্য: {goal.toLocaleString()} kcal
                </span>
              </div>

              <div className="flex items-center justify-around">
                {/* SVG Circular Progress Gauge */}
                <div className="relative w-36 h-36 flex items-center justify-center">
                  <svg className="w-full h-full transform -rotate-90" viewBox="0 0 100 100">
                    <circle cx="50" cy="50" r="42" fill="none" stroke="#e4f7ee" strokeWidth="12" />
                    <circle 
                      cx="50" cy="50" r="42" fill="none" stroke="#0e7a53" strokeWidth="12"
                      strokeDasharray="264" 
                      strokeDashoffset={dashOffset} 
                      strokeLinecap="round"
                      className="transition-all duration-700 ease-out"
                    />
                  </svg>
                  <div className="absolute flex flex-col items-center text-center">
                    <span className="text-2xl font-extrabold text-gray-900">{remaining.toLocaleString()}</span>
                    <span className="text-xs font-bold text-brand-500 leading-none">kcal বাকি</span>
                    <span className="text-[10px] text-gray-400">Remaining</span>
                  </div>
                </div>

                {/* Stats Breakdown */}
                <div className="space-y-2 text-xs">
                  <div>
                    <p className="text-gray-500">গৃহীত (Consumed)</p>
                    <p className="text-base font-bold text-flame-600">{consumed.toLocaleString()} kcal</p>
                  </div>
                  <div>
                    <p className="text-gray-500">লক্ষ্য (Goal)</p>
                    <p className="text-base font-bold text-gray-800">{goal.toLocaleString()} kcal</p>
                  </div>
                  <div>
                    <p className="text-gray-500">বাকি (Remaining)</p>
                    <p className="text-base font-bold text-brand-500">{remaining.toLocaleString()} kcal</p>
                  </div>
                </div>
              </div>
            </div>

            {/* Macros Breakdown Card */}
            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-4 shadow-sm space-y-3">
              <h3 className="text-sm font-bold text-gray-800">ম্যাক্রোনিউট্রিয়েন্ট (Macronutrients)</h3>
              
              <div>
                <div className="flex justify-between text-xs font-semibold mb-1">
                  <span className="text-gray-700 flex items-center gap-1.5"><span className="w-2 h-2 rounded-full bg-macro-protein"></span> প্রোটিন (Protein)</span>
                  <span className="text-gray-900">{protein}g / 120g</span>
                </div>
                <div className="w-full bg-rose-100 h-2 rounded-full overflow-hidden">
                  <div className="bg-macro-protein h-full rounded-full" style={{ width: `${Math.min(100, (protein/120)*100)}%` }}></div>
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-semibold mb-1">
                  <span className="text-gray-700 flex items-center gap-1.5"><span className="w-2 h-2 rounded-full bg-macro-carbs"></span> শর্করা (Carbs)</span>
                  <span className="text-gray-900">{carbs}g / 250g</span>
                </div>
                <div className="w-full bg-amber-100 h-2 rounded-full overflow-hidden">
                  <div className="bg-macro-carbs h-full rounded-full" style={{ width: `${Math.min(100, (carbs/250)*100)}%` }}></div>
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-semibold mb-1">
                  <span className="text-gray-700 flex items-center gap-1.5"><span className="w-2 h-2 rounded-full bg-macro-fat"></span> চর্বি (Fat)</span>
                  <span className="text-gray-900">{fat}g / 65g</span>
                </div>
                <div className="w-full bg-cyan-100 h-2 rounded-full overflow-hidden">
                  <div className="bg-macro-fat h-full rounded-full" style={{ width: `${Math.min(100, (fat/65)*100)}%` }}></div>
                </div>
              </div>
            </div>

            {/* Meals Section */}
            <div className="space-y-2">
              <h3 className="text-sm font-bold text-gray-800">খাবারের তালিকা (Meals)</h3>

              <div onClick={() => { setSelectedMeal('BREAKFAST'); setCurrentScreen('add-food'); }} className="cursor-pointer bg-white border border-[#e8ede9] rounded-2xl p-3.5 flex items-center justify-between hover:border-brand-500 transition">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-green-50 flex items-center justify-center text-lg">🍳</div>
                  <div>
                    <h4 className="text-sm font-bold text-gray-900">Breakfast (সকালের নাস্তা)</h4>
                    <p className="text-xs text-gray-500">২টি রুটি, ১টি ডিম, ১ কাপ চা</p>
                  </div>
                </div>
                <span className="text-xs font-bold px-2.5 py-1 bg-orange-50 text-flame-600 rounded-full">390 kcal</span>
              </div>

              <div onClick={() => { setSelectedMeal('LUNCH'); setCurrentScreen('add-food'); }} className="cursor-pointer bg-white border border-[#e8ede9] rounded-2xl p-3.5 flex items-center justify-between hover:border-brand-500 transition">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-green-50 flex items-center justify-center text-lg">🍛</div>
                  <div>
                    <h4 className="text-sm font-bold text-gray-900">Lunch (দুপুরের খাবার)</h4>
                    <p className="text-xs text-gray-500">২ প্লেট ভাত, রুই মাছ, ডাল</p>
                  </div>
                </div>
                <span className="text-xs font-bold px-2.5 py-1 bg-orange-50 text-flame-600 rounded-full">520 kcal</span>
              </div>

              <div onClick={() => { setSelectedMeal('SNACK'); setCurrentScreen('add-food'); }} className="cursor-pointer bg-white border border-[#e8ede9] rounded-2xl p-3.5 flex items-center justify-between hover:border-brand-500 transition">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-green-50 flex items-center justify-center text-lg">☕</div>
                  <div>
                    <h4 className="text-sm font-bold text-gray-900">Snack (বিকালের নাস্তা)</h4>
                    <p className="text-xs text-gray-500">১টি কলা, বিস্কুট</p>
                  </div>
                </div>
                <span className="text-xs font-bold px-2.5 py-1 bg-orange-50 text-flame-600 rounded-full">340 kcal</span>
              </div>

              <div onClick={() => { setSelectedMeal('DINNER'); setCurrentScreen('add-food'); }} className="cursor-pointer bg-white border border-[#e8ede9] rounded-2xl p-3.5 flex items-center justify-between hover:border-brand-500 transition">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-gray-50 flex items-center justify-center text-lg">🍲</div>
                  <div>
                    <h4 className="text-sm font-bold text-gray-900">Dinner (রাতের খাবার)</h4>
                    <p className="text-xs text-gray-400">এখনও যোগ করা হয়নি</p>
                  </div>
                </div>
                <button className="text-xs font-bold px-3 py-1 bg-brand-50 text-brand-600 rounded-full border border-brand-200">
                  + যোগ করুন
                </button>
              </div>
            </div>

            {/* Quick Add Button */}
            <button 
              onClick={() => { setSelectedMeal('LUNCH'); setCurrentScreen('add-food'); }}
              className="w-full py-3.5 bg-brand-500 hover:bg-brand-600 text-white font-bold rounded-2xl shadow-sm flex items-center justify-center gap-2 text-base transition"
            >
              <Plus className="w-5 h-5" /> খাবার যোগ করুন (Add Food)
            </button>
          </div>
        )}

        {/* ================= 2. ADD FOOD SCREEN ================= */}
        {currentScreen === 'add-food' && (
          <div className="space-y-4">
            <div className="flex items-center gap-2 mb-2">
              <button onClick={() => setCurrentScreen('home')} className="p-2 text-gray-600 hover:bg-gray-100 rounded-full">
                <ArrowLeft className="w-5 h-5" />
              </button>
              <h2 className="text-lg font-bold text-gray-900">খাবার যোগ করুন ({selectedMeal})</h2>
            </div>

            <div>
              <h3 className="text-xl font-bold text-brand-500">কি খেয়েছেন?</h3>
              <p className="text-sm text-gray-500">What did you eat?</p>
              <p className="text-xs text-gray-400 mt-1">সহজ বাংলায় বা ইংরেজিতে লিখে জানান, বাকিটা হিসাব করে দেব।</p>
            </div>

            {/* Suggestion Chips */}
            <div className="flex gap-2 overflow-x-auto no-scrollbar py-1">
              <button onClick={() => setNaturalInput('দুপুরে ২ প্লেট ভাত, ১ পিস রুই মাছ আর ডাল খেয়েছি')} className="whitespace-nowrap px-3 py-1 text-xs bg-gray-100 rounded-full border border-gray-200 text-gray-700">
                ২ প্লেট ভাত, রুই মাছ ও ডাল
              </button>
              <button onClick={() => setNaturalInput('সকালে ২টি আটার রুটি, ১টি ডিম ভাজি আর লাল চা')} className="whitespace-nowrap px-3 py-1 text-xs bg-gray-100 rounded-full border border-gray-200 text-gray-700">
                ২টি রুটি ও ডিম
              </button>
              <button onClick={() => setNaturalInput('১ কাপ দুধ চা ও ২টি সিংগাড়া')} className="whitespace-nowrap px-3 py-1 text-xs bg-gray-100 rounded-full border border-gray-200 text-gray-700">
                দুধ চা ও সিংগাড়া
              </button>
            </div>

            {/* Input Form */}
            <div className="space-y-3">
              <textarea 
                value={naturalInput} 
                onChange={e => { setNaturalInput(e.target.value); setHasAnalyzed(false); }}
                rows={4}
                placeholder="যেমন: দুপুরে ২ প্লেট ভাত, ১ পিস রুই মাছ আর ডাল খেয়েছি"
                className="w-full p-4 border border-[#d6e2d9] rounded-2xl focus:ring-2 focus:ring-brand-500 focus:outline-none text-base resize-none"
              />

              <button 
                onClick={handleAnalyze}
                disabled={isAnalyzing || !naturalInput.trim()}
                className="w-full py-3.5 bg-brand-500 hover:bg-brand-600 disabled:opacity-50 text-white font-bold rounded-2xl flex items-center justify-center gap-2 transition"
              >
                <Sparkles className="w-5 h-5" /> {isAnalyzing ? 'বিশ্লেষণ করা হচ্ছে...' : 'Analyze Food (খাবার বিশ্লেষণ করুন)'}
              </button>
            </div>

            {/* AI Detected Result */}
            {hasAnalyzed && (
              <div className="bg-white border-2 border-brand-500 rounded-2xl p-4 shadow-sm space-y-4">
                <div className="flex items-center justify-between">
                  <span className="text-sm font-bold text-brand-500 flex items-center gap-1.5">
                    <Sparkles className="w-4 h-4 text-brand-500" /> AI detected:
                  </span>
                  <span className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full font-semibold">৩টি খাবার</span>
                </div>

                <div className="space-y-2 text-sm">
                  <div className="flex items-center justify-between p-2.5 bg-gray-50 rounded-xl">
                    <span className="font-semibold">🍚 ভাত × 2 প্লেট</span>
                    <span className="font-bold text-flame-600">~400 kcal</span>
                  </div>
                  <div className="flex items-center justify-between p-2.5 bg-gray-50 rounded-xl">
                    <span className="font-semibold">🐟 রুই মাছ × 1 পিস</span>
                    <span className="font-bold text-flame-600">~150 kcal</span>
                  </div>
                  <div className="flex items-center justify-between p-2.5 bg-gray-50 rounded-xl">
                    <span className="font-semibold">🥣 ডাল × 1 বাটি</span>
                    <span className="font-bold text-flame-600">~70 kcal</span>
                  </div>
                </div>

                {/* Estimated calories */}
                <div className="bg-orange-50 border border-orange-100 rounded-xl p-3 flex justify-between items-center">
                  <div>
                    <p className="text-xs text-flame-600 font-medium">Estimated calories</p>
                    <p className="text-xl font-extrabold text-flame-600">~620 kcal</p>
                  </div>
                  <span className="text-[11px] bg-white text-flame-600 px-2 py-0.5 rounded-full border border-orange-200">
                    *আনুমানিক হিসাব
                  </span>
                </div>

                {/* Macros */}
                <div>
                  <p className="text-xs font-bold text-gray-700 mb-2">Estimated nutrition:</p>
                  <div className="grid grid-cols-3 gap-2 text-center text-xs">
                    <div className="p-2 bg-rose-50 text-macro-protein rounded-xl font-bold">Protein: 28g</div>
                    <div className="p-2 bg-amber-50 text-macro-carbs rounded-xl font-bold">Carbs: 82g</div>
                    <div className="p-2 bg-cyan-50 text-macro-fat rounded-xl font-bold">Fat: 18g</div>
                  </div>
                </div>

                {/* Buttons */}
                <div className="space-y-2 pt-2">
                  <button 
                    onClick={() => saveMeal(620)}
                    className="w-full py-3 bg-brand-500 hover:bg-brand-600 text-white font-bold rounded-xl transition flex items-center justify-center gap-1.5"
                  >
                    <Check className="w-4 h-4" /> Add to {selectedMeal}
                  </button>
                  <button 
                    onClick={() => setCurrentScreen('confirm')}
                    className="w-full py-3 bg-white border border-brand-500 text-brand-600 font-bold rounded-xl hover:bg-green-50 transition"
                  >
                    পরিমাণ সংশোধন করুন (Review & Edit)
                  </button>
                </div>
              </div>
            )}
          </div>
        )}

        {/* ================= 3. FOOD CONFIRMATION SCREEN ================= */}
        {currentScreen === 'confirm' && (
          <div className="space-y-4">
            <div className="flex items-center gap-2 mb-2">
              <button onClick={() => setCurrentScreen('add-food')} className="p-2 text-gray-600 hover:bg-gray-100 rounded-full">
                <ArrowLeft className="w-5 h-5" />
              </button>
              <h2 className="text-lg font-bold text-gray-900">খাবার যাচাই ও নিশ্চিতকরণ</h2>
            </div>

            <div className="bg-green-50 border border-green-200 p-3.5 rounded-2xl flex items-start gap-2.5">
              <Info className="w-5 h-5 text-brand-500 shrink-0 mt-0.5" />
              <div>
                <h4 className="text-xs font-bold text-brand-600">AI বিশ্লেষণ সংশোধন করুন</h4>
                <p className="text-[11px] text-gray-600">সংরক্ষণ করার আগে যেকোনো পরিমাণ বা একক পরিবর্তন অথবা বাদ দিতে পারেন।</p>
              </div>
            </div>

            {/* List of Editable Cards */}
            <div className="space-y-3">
              {foodItems.map(item => (
                <div key={item.id} className="bg-white border border-gray-200 rounded-2xl p-3.5 shadow-sm space-y-2">
                  <div className="flex justify-between items-start">
                    <div className="flex items-center gap-2.5">
                      <span className="text-2xl">{item.icon}</span>
                      <div>
                        <h4 className="font-bold text-sm text-gray-900">{item.name}</h4>
                        <p className="text-xs text-gray-400">একক: {item.unit}</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      <span className="text-xs font-bold px-2 py-0.5 bg-orange-50 text-flame-600 rounded-full">
                        ~{item.qty * item.kcalPerUnit} kcal
                      </span>
                      <button onClick={() => removeItem(item.id)} className="text-red-400 hover:text-red-600 p-1">
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </div>

                  <div className="flex justify-between items-center pt-1 border-t border-gray-50 text-xs">
                    <span className="text-gray-500 font-medium">পরিমাণ: {item.qty} {item.unit}</span>
                    <div className="flex items-center gap-2">
                      <button 
                        onClick={() => updateQuantity(item.id, -1)}
                        className="w-7 h-7 rounded-full bg-gray-100 hover:bg-gray-200 font-bold flex items-center justify-center text-sm"
                      >
                        <Minus className="w-3.5 h-3.5" />
                      </button>
                      <span className="font-bold text-sm px-1">{item.qty}</span>
                      <button 
                        onClick={() => updateQuantity(item.id, 1)}
                        className="w-7 h-7 rounded-full bg-green-100 hover:bg-green-200 text-brand-600 font-bold flex items-center justify-center text-sm"
                      >
                        <Plus className="w-3.5 h-3.5" />
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>

            {/* Add Another Food Button */}
            <button 
              onClick={() => {
                const name = prompt('খাবারের নাম লিখুন:', 'সালাদ');
                if (name) {
                  setFoodItems(prev => [...prev, {
                    id: Date.now(),
                    name,
                    icon: '🥗',
                    qty: 1,
                    unit: 'বাটি',
                    kcalPerUnit: 50,
                    protein: 1,
                    carbs: 8,
                    fat: 1
                  }]);
                }
              }}
              className="w-full py-2.5 border-2 border-dashed border-gray-300 hover:border-brand-500 text-gray-600 hover:text-brand-600 font-bold rounded-2xl text-xs transition"
            >
              + আরেকটি খাবার যোগ করুন (Add Another Food)
            </button>

            {/* Total and Save */}
            <div className="bg-white border border-gray-200 rounded-2xl p-4 shadow-md space-y-3">
              <div className="flex justify-between items-center">
                <div>
                  <p className="text-xs text-gray-500">মোট পুষ্টিমান (Total)</p>
                  <p className="text-xl font-bold text-flame-600">~{totalKcal} kcal</p>
                </div>
                <div className="text-xs text-gray-500 font-medium">
                  P: {totalProtein}g • C: {totalCarbs}g • F: {totalFat}g
                </div>
              </div>

              <button 
                onClick={() => saveMeal(totalKcal)}
                className="w-full py-3.5 bg-brand-500 hover:bg-brand-600 text-white font-bold rounded-xl transition flex items-center justify-center gap-1.5"
              >
                <Check className="w-4 h-4" /> Add to {selectedMeal}
              </button>
            </div>
          </div>
        )}

        {/* ================= 4. DIARY SCREEN ================= */}
        {currentScreen === 'diary' && (
          <div className="space-y-4">
            <div>
              <h2 className="text-xl font-bold text-gray-900">October 8, 2026</h2>
              <p className="text-sm font-semibold text-brand-500">Thursday (বৃহস্পতিবার)</p>
            </div>

            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-4 flex justify-between items-center shadow-sm">
              <div>
                <p className="text-xs text-gray-500">দৈনিক ক্যালোরি হিসাব (Daily Summary)</p>
                <p className="text-xl font-extrabold text-flame-600">{consumed.toLocaleString()} / {goal.toLocaleString()} kcal</p>
              </div>
              <span className="text-xs font-bold text-brand-600 bg-green-50 px-2.5 py-1 rounded-full border border-green-200">
                {remaining.toLocaleString()} kcal বাকি
              </span>
            </div>

            {/* Meals Accordion */}
            <div className="space-y-3">
              <div className="bg-white border border-[#e8ede9] rounded-2xl p-4 shadow-sm space-y-2">
                <div className="flex justify-between items-center">
                  <span className="font-bold text-sm">🍳 Breakfast (সকালের নাস্তা)</span>
                  <span className="text-xs font-bold text-flame-600">390 kcal</span>
                </div>
                <ul className="text-xs text-gray-600 space-y-1 pl-2 border-l-2 border-brand-100">
                  <li>• 2 Roti (আটার রুটি) - 220 kcal</li>
                  <li>• 1 Egg (ডিম সিদ্ধ) - 78 kcal</li>
                  <li>• 1 cup Milk (দুধ) - 92 kcal</li>
                </ul>
              </div>

              <div className="bg-white border border-[#e8ede9] rounded-2xl p-4 shadow-sm space-y-2">
                <div className="flex justify-between items-center">
                  <span className="font-bold text-sm">🍛 Lunch (দুপুরের খাবার)</span>
                  <span className="text-xs font-bold text-flame-600">520 kcal</span>
                </div>
                <ul className="text-xs text-gray-600 space-y-1 pl-2 border-l-2 border-brand-100">
                  <li>• 2 cups Rice (সাদা ভাত) - 300 kcal</li>
                  <li>• 1 piece Rui Fish (রুই মাছ) - 150 kcal</li>
                  <li>• 1 bowl Dal (মসুর ডাল) - 70 kcal</li>
                </ul>
              </div>

              <div className="bg-white border border-[#e8ede9] rounded-2xl p-4 shadow-sm space-y-2">
                <div className="flex justify-between items-center">
                  <span className="font-bold text-sm">☕ Snack (বিকালের নাস্তা)</span>
                  <span className="text-xs font-bold text-flame-600">340 kcal</span>
                </div>
                <ul className="text-xs text-gray-600 space-y-1 pl-2 border-l-2 border-brand-100">
                  <li>• Banana (পাকা কলা) - 105 kcal</li>
                  <li>• Biscuits (বিস্কুট) - 235 kcal</li>
                </ul>
              </div>

              <div className="bg-white border border-[#e8ede9] rounded-2xl p-4 shadow-sm space-y-2">
                <div className="flex justify-between items-center">
                  <span className="font-bold text-sm text-gray-500">🍲 Dinner (রাতের খাবার)</span>
                  <span className="text-xs text-gray-400">0 kcal</span>
                </div>
                <p className="text-xs text-gray-400 italic">No food added (এখনও যোগ করা হয়নি)</p>
              </div>
            </div>

            <button 
              onClick={() => { setSelectedMeal('DINNER'); setCurrentScreen('add-food'); }}
              className="w-full py-3 bg-brand-500 hover:bg-brand-600 text-white font-bold rounded-2xl transition"
            >
              + খাবার যোগ করুন (+ Add Food)
            </button>
          </div>
        )}

        {/* ================= 5. PROGRESS SCREEN ================= */}
        {currentScreen === 'progress' && (
          <div className="space-y-4">
            <div>
              <h2 className="text-xl font-bold text-gray-900">অগ্রগতি ও রিপোর্ট (Progress)</h2>
              <p className="text-xs text-gray-500">সাপ্তাহিক ক্যালোরি ও ওজনের সামগ্রিক চিত্র</p>
            </div>

            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-4 shadow-sm space-y-4">
              <div className="flex justify-between items-center">
                <span className="text-sm font-bold text-gray-800 flex items-center gap-1.5">
                  <Flame className="w-4 h-4 text-flame-500" /> সাপ্তাহিক ক্যালোরি (Weekly)
                </span>
                <span className="text-xs bg-green-50 text-brand-600 px-2 py-0.5 rounded-full font-bold">
                  গড়: 1,830 kcal
                </span>
              </div>

              <div className="flex items-end justify-between h-36 pt-4 pb-2 border-b border-gray-100 px-1">
                {[
                  { day: 'Mon', h: '75%', color: 'bg-brand-100' },
                  { day: 'Tue', h: '85%', color: 'bg-flame-500' },
                  { day: 'Wed', h: '70%', color: 'bg-brand-100' },
                  { day: 'Thu', h: '52%', color: 'bg-brand-500', active: true },
                  { day: 'Fri', h: '80%', color: 'bg-brand-100' },
                  { day: 'Sat', h: '90%', color: 'bg-flame-500' },
                  { day: 'Sun', h: '78%', color: 'bg-brand-100' }
                ].map(b => (
                  <div key={b.day} className="flex flex-col items-center gap-1 w-8">
                    <div className={`w-4 ${b.color} rounded-t-full`} style={{ height: b.h }}></div>
                    <span className={`text-[10px] ${b.active ? 'font-bold text-brand-500' : 'text-gray-500'}`}>{b.day}</span>
                  </div>
                ))}
              </div>

              <div className="grid grid-cols-2 text-xs text-gray-600 gap-1.5 pt-1">
                <div>• Monday: 1,850 kcal</div>
                <div>• Friday: 1,950 kcal</div>
                <div>• Tuesday: 2,020 kcal</div>
                <div>• Saturday: 2,100 kcal</div>
                <div>• Wednesday: 1,760 kcal</div>
                <div>• Sunday: 1,880 kcal</div>
                <div className="font-bold text-brand-600">• Thursday (আজ): 1,250 kcal</div>
              </div>
            </div>

            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-4 shadow-sm space-y-3">
              <h3 className="text-sm font-bold text-gray-800">⚖️ ওজন অগ্রগতি (Weight Progress)</h3>
              <div className="grid grid-cols-3 gap-2 text-center text-xs">
                <div className="bg-gray-50 p-2.5 rounded-xl">
                  <p className="text-gray-400">শুরু</p>
                  <p className="text-base font-bold text-gray-800">{profile.weight} kg</p>
                </div>
                <div className="bg-green-50 p-2.5 rounded-xl border border-green-200">
                  <p className="text-brand-600">বর্তমান</p>
                  <p className="text-base font-bold text-brand-600">{profile.weight} kg</p>
                </div>
                <div className="bg-orange-50 p-2.5 rounded-xl">
                  <p className="text-flame-600">লক্ষ্য</p>
                  <p className="text-base font-bold text-flame-600">{profile.goalWeight} kg</p>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ================= 6. PROFILE SCREEN ================= */}
        {currentScreen === 'profile' && (
          <div className="space-y-4">
            <div className="flex items-center gap-3">
              <div className="w-14 h-14 bg-green-100 rounded-full flex items-center justify-center text-2xl">👤</div>
              <div>
                <h2 className="text-lg font-bold text-gray-900">{profile.name}</h2>
                <p className="text-xs text-brand-500 font-semibold">BanglaCal সদস্য</p>
              </div>
            </div>

            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-4 shadow-sm space-y-3">
              <div className="flex justify-between items-center">
                <h3 className="text-sm font-bold text-gray-800">শারীরিক তথ্য ও লক্ষ্য</h3>
                <button 
                  onClick={() => {
                    const newName = prompt('নাম পরিবর্তন করুন:', profile.name);
                    if (newName) setProfile(p => ({ ...p, name: newName }));
                  }}
                  className="text-xs text-brand-600 font-bold px-2.5 py-1 bg-green-50 rounded-lg hover:bg-green-100"
                >
                  Edit Profile
                </button>
              </div>

              <div className="text-xs space-y-2 text-gray-600">
                <div className="flex justify-between py-1 border-b border-gray-50">
                  <span>Age (বয়স)</span>
                  <span className="font-bold text-gray-900">{profile.age}</span>
                </div>
                <div className="flex justify-between py-1 border-b border-gray-50">
                  <span>Height (উচ্চতা)</span>
                  <span className="font-bold text-gray-900">{profile.height}</span>
                </div>
                <div className="flex justify-between py-1 border-b border-gray-50">
                  <span>Weight (ওজন)</span>
                  <span className="font-bold text-gray-900">{profile.weight} kg</span>
                </div>
                <div className="flex justify-between py-1 border-b border-gray-50">
                  <span>Goal (লক্ষ্য)</span>
                  <span className="font-bold text-gray-900">{profile.goal}</span>
                </div>
                <div className="flex justify-between py-1 border-b border-gray-50">
                  <span>Activity level</span>
                  <span className="font-bold text-gray-900">{profile.activityLevel}</span>
                </div>
                <div className="flex justify-between py-1 text-brand-600 font-bold">
                  <span>Daily calorie goal</span>
                  <span>{goal.toLocaleString()} kcal</span>
                </div>
              </div>
            </div>

            {/* Settings */}
            <div className="bg-white border border-[#e2e8e4] rounded-2xl p-4 shadow-sm space-y-3 text-xs">
              <h3 className="text-sm font-bold text-gray-800">সেটিংস</h3>
              <div className="flex justify-between items-center py-2 border-b border-gray-100">
                <div>
                  <p className="font-bold text-gray-800">Language (ভাষা)</p>
                  <p className="text-[11px] text-gray-400">Bangla / English</p>
                </div>
                <button 
                  onClick={() => setProfile(p => ({ ...p, isBangla: !p.isBangla }))}
                  className="px-2.5 py-1 bg-gray-100 rounded-full font-bold hover:bg-gray-200"
                >
                  {profile.isBangla ? 'বাংলা' : 'English'}
                </button>
              </div>
              <div className="flex justify-between items-center py-2 border-b border-gray-100">
                <div>
                  <p className="font-bold text-gray-800">Notifications (বিজ্ঞপ্তি)</p>
                  <p className="text-[11px] text-gray-400">খাবারের অনুস্মারক</p>
                </div>
                <span className="text-brand-600 font-bold">চালু (On)</span>
              </div>
              <div className="flex justify-between items-center py-2">
                <div>
                  <p className="font-bold text-gray-800">Dark Mode (ডার্ক মোড)</p>
                  <p className="text-[11px] text-gray-400">লাইট / ডার্ক থিম</p>
                </div>
                <span className="text-gray-400 font-bold">Off</span>
              </div>
            </div>
          </div>
        )}
      </main>

      {/* Bottom Navigation */}
      <nav className="fixed bottom-0 w-full max-w-md bg-white border-t border-gray-200 flex justify-around py-2 z-30">
        <button 
          onClick={() => setCurrentScreen('home')}
          className={`flex flex-col items-center gap-1 text-xs font-semibold ${currentScreen === 'home' ? 'text-brand-500 font-bold' : 'text-gray-400 hover:text-brand-500'}`}
        >
          <Home className="w-5 h-5" />
          <span>হোম</span>
        </button>
        <button 
          onClick={() => setCurrentScreen('diary')}
          className={`flex flex-col items-center gap-1 text-xs font-semibold ${currentScreen === 'diary' ? 'text-brand-500 font-bold' : 'text-gray-400 hover:text-brand-500'}`}
        >
          <BookOpen className="w-5 h-5" />
          <span>ডায়েরি</span>
        </button>
        <button 
          onClick={() => setCurrentScreen('progress')}
          className={`flex flex-col items-center gap-1 text-xs font-semibold ${currentScreen === 'progress' ? 'text-brand-500 font-bold' : 'text-gray-400 hover:text-brand-500'}`}
        >
          <TrendingUp className="w-5 h-5" />
          <span>অগ্রগতি</span>
        </button>
        <button 
          onClick={() => setCurrentScreen('profile')}
          className={`flex flex-col items-center gap-1 text-xs font-semibold ${currentScreen === 'profile' ? 'text-brand-500 font-bold' : 'text-gray-400 hover:text-brand-500'}`}
        >
          <User className="w-5 h-5" />
          <span>প্রোফাইল</span>
        </button>
      </nav>
    </div>
  );
}
