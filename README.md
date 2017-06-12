<p><strong>使用框架：</strong>SpringBoot+Thymeleaf+DBUtils</p>
<p>该项目实现了将Image插入到MySQL中的Blob字段中，并且能够读取并显示在前端上</p>
<br/>
<p>不过还是<strong>不推荐将Image放入SQL中</strong>，因为在做数据库备份时这些Image会十分影响备份效率</p>
<p>所以推荐大家存放Image还是使用FileSystem</p>
<p>Blob字段最多还是用在存放长篇文章，即varchar不够用的时候使用</p>
