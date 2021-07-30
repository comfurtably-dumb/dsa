def merge(l, s, e):
    if s == e:
        return
    m = s + int((e - s)/2)
    s1 = s
    s2 = m + 1
    e1 = m
    e2 = e
    merge(l, s1, e1)
    merge(l, s2, e2)
    i = s1
    j = s2
    l1 = [0 for k in range(e - s + 1)]
    k = 0
    while (i <= e1 or j <= e2) and k <= e - s + 1:
        if i <= e1 and j <= e2:
            if l[j] < l[i]:
                l1[k] = l[j]
                j += 1
            else:
                l1[k] = l[i]
                i += 1
        elif i > e1 and j <= e2:
            l1[k] = l[j]
            j += 1
        elif i <= e1 and j > e2:
            l1[k] = l[i]
            i += 1
        k += 1
    #print(s1,e1,s2,e2,s,e,l1)
    for x in range(s, e + 1):
        l[x] = l1[x - s]

l = [4,3,44,8,-44,7,-10]
merge(l,0,6)
print(l)



    