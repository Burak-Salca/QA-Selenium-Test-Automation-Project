# 🧪 QA Selenium Test Otomasyon Projesi

## 📌 Proje Açıklaması
Bu proje, **Selenium WebDriver, Java ve TestNG kullanarak bir web uygulamasının başarılı bir kayıt ve başarılı bir giriş test otomasyonunu gerçekleştirmek amacıyla geliştirilmiştir**.  
Pozitif ve negatif test senaryoları ile **kapsamlı bir test altyapısı oluşturulmuştur**. Ayrıca, **Allure Raporlama Sistemi** kullanılarak test sonuçları analiz edilmiştir.

## 📊 **Otomasyon Testi Raporlaması**
Bu projede **Allure raporları** kullanılarak test süreçleri **adım adım kaydedilmiş ve analiz edilmiştir**.  

✅ **Testlerin her aşaması detaylı şekilde raporlandı**  
✅ **Kritik test adımlarında ekran görüntüleri alınıp raporlara eklendi**  
✅ **Başarılı ve başarısız senaryoların tüm aşamaları raporda görüntülenebilir**

📸 **Ekran görüntüleri sayesinde hata tespiti kolaylaştırılmış ve manuel doğrulama gereksinimi en aza indirilmiştir.**  


✅ **Test Sonuçlarına Buradan Ulaşabilirsin:**  
🔗 [Test Sonuçları](https://qa-selenium-test-automation-project.vercel.app/)

---

## 🛠 **Kullanılan Teknolojiler**
| Teknoloji | Açıklama |
|-----------|---------|
| 🖥 **Java** | Test senaryoları için ana programlama dili |
| 🏎 **Selenium WebDriver** | Web uygulamasının otomatik test edilmesi için |
| 🧪 **TestNG** | Testlerin düzenlenmesi ve çalıştırılması için |
| 📊 **Allure Report** | Test sonuçlarının görselleştirilmesi için |
| 🐧 **Maven** | Bağımlılık yönetimi ve test çalıştırma için |
| ☁️ **Vercel** | Test raporlarının barındırılması için |

---

## 📑 **Test Senaryoları**
Bu projede **pozitif ve negatif test senaryoları** uygulanmıştır.  
📌 **Aşağıdaki tablo, hangi testlerin uygulandığını özetlemektedir:**

### ✅ **Pozitif Test Senaryoları**
| Test Adı | Açıklama |
|----------|---------|
| 🟢 **Başarılı Kayıt ve Giriş** | Kullanıcı yeni bir hesap oluşturur, e-posta doğrulaması yapar ve sisteme giriş yaparak ana sayfaya ulaşır. |

---

### ❌ **Negatif Test Senaryoları**
| Test Adı | Açıklama |
|----------|---------|
| 🔴 **Kayıtlı Olmayan E-posta ile Giriş** | Var olmayan bir e-posta ile giriş yapma testi |
| 🔴 **Geçersiz E-posta Formatı ile Giriş** | Yanlış formatta (örneğin, `useremail.com`) e-posta ile giriş denemesi |
| 🔴 **Yanlış Şifre Tekrarı ile Kayıt Olma** | Şifre ve şifre tekrarı uyuşmadığında hata mesajının çıktığını doğrulama |
| 🔴 **Yanlış Doğrulama Kodu ile Kayıt Tamamlama** | E-posta doğrulama kodunun yanlış girildiğinde hata mesajı verilmesi |

